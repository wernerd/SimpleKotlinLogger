package com.example.werner.logger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LoggerActivity : AppCompatActivity() {

    val LOG = LoggerApp.logger.getLoggerKt(this::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logger)
        val universalAnswer = 42
        LOG.debug { "This is a simple string" }
        LOG.debug ({"A slf4j format {} and {}"}, {arrayOf(this, universalAnswer)})
        LOG.debug {"A string using Kotlin format $this and $universalAnswer"}
    }
}
