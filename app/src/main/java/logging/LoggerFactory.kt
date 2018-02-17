package logging

import com.example.werner.logger.ListLogAppender
import java.util.HashSet

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
     * Return an appropriate [LoggerJv] instance as specified by the
     * `name` parameter.
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
     * Return an appropriate Kotlin [LoggerKt] instance as specified by the
     * `name` parameter.
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
