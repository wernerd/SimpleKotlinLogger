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

class LoggerFactory {
    private val simpleLogAppender = SimpleLogAppender()
    private val androidLogAppender = AndroidLogAppender()

    /** the current set level  */
    @Volatile var level = Level.ALL
    internal val appenders: MutableSet<LoggerAppender> = HashSet()

    init {
        appenders.add(simpleLogAppender)
    }

    /**
     * Return an appropriate Java [LoggerJv] logger instance as specified by the `name` parameter.
     *
     * This logger instance provides the logger functions for Java.
     *
     * Null-valued name arguments are considered invalid.
     *
     * Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the LoggerJv to return
     * @return a LoggerJv instance
     */
    fun getLoggerJv(name: String): LoggerImplementationJv =
            LoggerImplementationJv(if (name.length > 23) name.substring(0, 22) else name, this)


    /**
     * Return an appropriate Kotlin [LoggerKt] logger instance as specified by the `name` parameter.
     *
     * Null-valued name arguments are considered invalid.
     *
     * Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the LoggerJv to return
     * @return a LoggerKt instance
     */
    fun getLoggerKt(name: String): LoggerImplementationKt =
            LoggerImplementationKt(if (name.length > 23) name.substring(0, 22) else name, this)

    fun getLoggerJv(clazz: Class<*>): LoggerImplementationJv = getLoggerJv(clazz.simpleName)
    fun getLoggerKt(clazz: Class<*>): LoggerImplementationKt = getLoggerKt(clazz.simpleName)

    fun unitTesting(): LoggerFactory {
        appenders.remove(androidLogAppender)
        appenders.add(simpleLogAppender)
        return this
    }

    fun android() : LoggerFactory {
        appenders.remove(simpleLogAppender)
        appenders.add(androidLogAppender)
        return this
    }

    fun addAppender(appender: LoggerAppender) {
        appenders.add(appender)
    }

    fun removeAppender(appender: LoggerAppender) {
        appenders.remove(appender)
    }

    fun withLevel(level: Level) : LoggerFactory {
        this.level = level
        return this
    }
}
