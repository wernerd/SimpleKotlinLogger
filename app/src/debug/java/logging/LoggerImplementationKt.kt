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

@Suppress("unused")
class LoggerImplementationKt internal constructor(override val name: String, private val factory: LoggerFactory) : LoggerKt {

    fun doLogFormatted(level: Level, message: String, throwable: Throwable?) {
        for (appender in factory.appenders) {
            appender.append(name, level, message, throwable)
        }
    }

    @Suppress("unchecked_cast")
    fun doLog(level: Level, format: String, arg: Any?) {
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

    override val isTraceEnabled: Boolean = factory.level == Level.ALL

    inline fun trace(msg: () -> String) {
        doLogFormatted(Level.ALL, msg(), null)
    }

    inline fun trace(format: () -> String, vararg arguments: Any) {
        doLog(Level.ALL, format(), arguments)
    }

    inline fun trace(msg: () -> String, t: Throwable) {
        doLogFormatted(Level.ALL, msg(), t)
    }

    override val isDebugEnabled: Boolean = Level.DEBUG == factory.level

    inline fun debug(msg: () -> String) {
        doLogFormatted(Level.DEBUG, msg(), null)
    }

    // Probably rarely used: use Kotlin's string template handling to setup the debug message
    // and use the function above. Otherwise use it like:
    // debug({"Some format {} string {}"}, {arrayOf(arg1, arg2)})
    inline fun debug(format: () -> String, arguments: () -> Array<Any>) {
        doLog(Level.DEBUG, format(), arguments())
    }

    inline fun debug(msg: () -> String, t: Throwable) {
        doLogFormatted(Level.DEBUG, msg(), t)
    }

    override val isInfoEnabled: Boolean = factory.level.isLoggable(Level.INFO)

    inline fun info(msg: () -> String) {
        doLogFormatted(Level.INFO, msg(), null)
    }

    inline fun info(format: () -> String, arguments: () -> Array<Any>) {
        doLog(Level.INFO, format(), arguments())
    }

    inline fun info(msg: () -> String, t: Throwable) {
        doLogFormatted(Level.INFO, msg(), t)
    }

    override val isWarnEnabled: Boolean = false

    inline fun warn(msg: () -> String) {
        doLogFormatted(Level.WARN, msg(), null)
    }

    inline fun warn(format: () -> String, arguments: () -> Array<Any>) {
        doLog(Level.WARN, format(), arguments())
    }

    inline fun warn(msg: () -> String, t: Throwable) {
        doLogFormatted(Level.WARN, msg(), t)
    }

    override val isErrorEnabled: Boolean = true

    inline fun error(msg: () -> String) {
        doLogFormatted(Level.ERROR, msg(), null)
    }

    inline fun error(format: () -> String, arguments: () -> Array<Any>) {
        doLog(Level.ERROR, format(), arguments())
    }

    inline fun error(msg: () -> String, t: Throwable) {
        doLogFormatted(Level.ERROR, msg(), t)
    }
}
