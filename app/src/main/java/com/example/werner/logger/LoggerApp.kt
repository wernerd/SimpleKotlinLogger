package com.example.werner.logger

import android.app.Application
import logging.LoggerFactory

/**
 * Simple app class to get app context.
 *
 * Created by werner on 17.02.18.
 */
class LoggerApp : Application() {


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        logger.android()
        logger.addAppender(listLogAppender)
    }

    companion object {
        private lateinit var INSTANCE: LoggerApp
        fun appContext() = INSTANCE.applicationContext

        val logger = LoggerFactory()
        val listLogAppender = ListLogAppender()


    }
}
