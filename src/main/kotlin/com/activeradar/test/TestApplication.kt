package com.activeradar.test

import com.samskivert.mustache.Mustache
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class TestApplication {
    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)

    @Bean
    fun databaseInitializer(userRepository: UserRepository, articleRepository: ArticleRepository) = CommandLineRunner {
        val user = User("derp", "Derp", "McDerp")
        userRepository.save(user)

        articleRepository.save(Article(
                "A Test Article",
                "This is a Test",
                "Lorem **derp** Blargh blargh, https://google.com",
                user,
                1
        ))

        articleRepository.save(Article(
                "Another Test Article",
                "This is another Test",
                "Lorem **derpDerp** Blargh, https://stackoverflow.com",
                user,
                2
        ))
    }
}

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
