/*
 * Copyright 2018 Werner Dittmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.werner.logger

import android.app.Application
import android.content.Context
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
        fun appContext(): Context = INSTANCE.applicationContext

        val logger = LoggerFactory()
        val listLogAppender = ListLogAppender()
    }
}
