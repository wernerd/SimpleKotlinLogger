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

package logging

import android.util.Log

/**
 * A simple appender which logs to the android log system.
 */
class AndroidLogAppender : LoggerAppender {
    override fun append(name: String, level: Level, message: String, throwable: Throwable?) {
        val logMessage = if (throwable == null) message else message + '\n' + Log.getStackTraceString(throwable)
        when (level) {
            Level.ERROR -> Log.e(name, logMessage)
            Level.WARN -> Log.w(name, logMessage)
            Level.INFO -> Log.i(name, logMessage)
            else -> Log.d(name, logMessage)
        }
    }
}
