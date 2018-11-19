package com.activeradar.test

import org.springframework.boot.Banner
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
class AppProperties {
    lateinit var title:String
    val banner = Banner()

    class Banner {
        var title:String? = null
        lateinit var content:String
    }
}
