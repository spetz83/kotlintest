package com.activeradar.test

import com.samskivert.mustache.Mustache
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TestApplication {
    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)
}

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
