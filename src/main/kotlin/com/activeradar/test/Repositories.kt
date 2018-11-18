package com.activeradar.test

import org.springframework.data.repository.CrudRepository

interface ArticleRepository:CrudRepository<Article, Long> {
    fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository:CrudRepository<User, String>