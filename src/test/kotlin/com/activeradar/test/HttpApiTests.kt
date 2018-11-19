package com.activeradar.test

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
class HttpApiTests(@Autowired val mockMvc:MockMvc) {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var articleRepository: ArticleRepository

    @MockBean
    private lateinit var markdownConverter: MarkdownConverter

    private lateinit var users:List<User>

    @BeforeAll
    fun setup() {
        val user1 = User("testuser", "Test", "User")
        val user2 = User("testuser2", "Test2", "User2")

        users = listOf(user1, user2)
    }

    @Test
    fun `List articles`() {
        //TODO: Figure out how to use the setup method to provide user
        val user = users[0]
        val article1 = Article("Article 1", "Article 1 Headline", "Article 1 content", user, 1)
        val article2 = Article("Article 2", "Article 2 Headline", "Article 2 content", user, 2)

        whenever(articleRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(article1, article2))
        whenever(markdownConverter.invoke(any())).thenAnswer { it.arguments[0] }
        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("\$.[0].author.login").value(user.login))
            .andExpect(jsonPath("\$.[0].id").value(article1.id!!))
            .andExpect(jsonPath("\$.[1].author.login").value(user.login))
            .andExpect(jsonPath("\$.[1].id").value(article2.id!!))

    }

    @Test
    fun `List users`() {
        whenever(userRepository.findAll()).thenReturn(users)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("\$.[0].login").value(users[0].login))
            .andExpect(jsonPath("\$.[1].login").value(users[1].login))
    }
}
