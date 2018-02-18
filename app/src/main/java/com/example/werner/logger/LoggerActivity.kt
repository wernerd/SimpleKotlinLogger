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

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_logger.*

class LoggerActivity : AppCompatActivity() {

    private val log = LoggerApp.logger.getLoggerKt(this::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logger)
        val universalAnswer = 42
        log.debug { "This is a simple string" }
        log.debug ({"A slf4j format {} and {}"}, {arrayOf(this, universalAnswer)})
        log.debug {"A string using Kotlin format $this and $universalAnswer"}

        showLog.setOnClickListener({
            val intent = Intent(this, ShowListActivity::class.java)
            startActivity(intent)})
    }
}
