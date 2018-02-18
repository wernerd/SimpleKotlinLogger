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

class LoggerImplementationJv internal constructor(override val name: String, private val factory: LoggerFactory) : LoggerJv {

    private fun doLogFormatted(level: Level, message: String, throwable: Throwable?) {
        for (appender in factory.appenders) {
            appender.append(name, level, message, throwable)
        }
    }

    @Suppress("unchecked_cast")
    private fun doLog(level: Level, format: String, arg: Any?) {
        if (level.isLoggable(factory.level)) {
            if (arg == null) {
                doLogFormatted(level, format, null)
            } else {
                val t = if (arg is Array<*>) {
                    MessageFormatter.arrayFormat(format, (arg as Array<Any?>))
                } else {
                    MessageFormatter.format(format, arg)
                }
                doLogFormatted(level, t.message, t.throwable)
            }
        }
    }

    private fun doLog(level: Level, format: String, arg1: Any, arg2: Any) {
        if (level.isLoggable(factory.level)) {
            val t = MessageFormatter.format(format, arg1, arg2)
            doLogFormatted(level, t.message, t.throwable)
        }
    }

    override fun isTraceEnabled() = Level.ALL.isLoggable(factory.level)

    override fun trace(msg: String) {
        doLogFormatted(Level.ALL, msg, null)
    }

    override fun trace(format: String, arg: Any) {
        doLog(Level.ALL, format, arg)
    }

    override fun trace(format: String, arg1: Any, arg2: Any) {
        doLog(Level.ALL, format, arg1, arg2)
    }

    override fun trace(format: String, vararg arguments: Any) {
        doLog(Level.ALL, format, arguments)
    }

    override fun trace(msg: String, t: Throwable) {
        doLogFormatted(Level.ALL, msg, t)
    }

    override fun isDebugEnabled() = Level.DEBUG.isLoggable(factory.level)

    override fun debug(msg: String) {
        doLogFormatted(Level.DEBUG, msg, null)
    }

    override fun debug(format: String, arg: Any) {
        doLog(Level.DEBUG, format, arg)
    }

    override fun debug(format: String, arg1: Any, arg2: Any) {
        doLog(Level.DEBUG, format, arg1, arg2)
    }

    override fun debug(format: String, vararg arguments: Any) {
        doLog(Level.DEBUG, format, arguments)
    }

    override fun debug(msg: String, t: Throwable) {
        doLogFormatted(Level.DEBUG, msg, t)
    }

    override fun isInfoEnabled() = Level.INFO.isLoggable(factory.level)

    override fun info(msg: String) {
        doLogFormatted(Level.INFO, msg, null)
    }

    override fun info(format: String, arg: Any) {
        doLog(Level.INFO, format, arg)
    }

    override fun info(format: String, arg1: Any, arg2: Any) {
        doLog(Level.INFO, format, arg1, arg2)
    }

    override fun info(format: String, vararg arguments: Any) {
        doLog(Level.INFO, format, arguments)
    }

    override fun info(msg: String, t: Throwable) {
        doLogFormatted(Level.INFO, msg, t)
    }

    override fun isWarnEnabled() = Level.WARN.isLoggable(factory.level)

    override fun warn(msg: String) {
        doLogFormatted(Level.WARN, msg, null)
    }

    override fun warn(format: String, arg: Any) {
        doLog(Level.WARN, format, arg)
    }

    override fun warn(format: String, vararg arguments: Any) {
        doLog(Level.WARN, format, arguments)
    }

    override fun warn(format: String, arg1: Any, arg2: Any) {
        doLog(Level.WARN, format, arg1, arg2)
    }

    override fun warn(msg: String, t: Throwable) {
        doLogFormatted(Level.WARN, msg, t)
    }

    override fun isErrorEnabled() = Level.ERROR.isLoggable(factory.level)

    override fun error(msg: String) {
        doLogFormatted(Level.ERROR, msg, null)
    }

    override fun error(format: String, arg: Any) {
        doLog(Level.ERROR, format, arg)
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
        doLog(Level.ERROR, format, arg1, arg2)
    }

    override fun error(format: String, vararg arguments: Any) {
        doLog(Level.ERROR, format, arguments)
    }

    override fun error(msg: String, t: Throwable) {
        doLogFormatted(Level.ERROR, msg, t)
    }

}
