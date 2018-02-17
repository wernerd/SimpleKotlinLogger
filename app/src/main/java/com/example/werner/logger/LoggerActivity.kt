package com.example.werner.logger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LoggerActivity : AppCompatActivity() {

    private val log = LoggerApp.logger.getLoggerKt(this::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logger)
        val universalAnswer = 42
        log.debug { "This is a simple string" }
        log.debug ({"A slf4j format {} and {}"}, {arrayOf(this, universalAnswer)})
        log.debug {"A string using Kotlin format $this and $universalAnswer"}
    }
}
