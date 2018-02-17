
package logging

@Suppress("UNUSED_PARAMETER", "unused")
class LoggerImplementationKt internal constructor(override val name: String, private val factory: ComfyLogFactory) : LoggerKt {

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

    inline fun trace(msg: () -> String) {}

    inline fun trace(format: () -> String, arguments: () -> Array<Any>) {}

    inline fun trace(msg: () -> String, t: Throwable) {}

    override val isDebugEnabled: Boolean = Level.DEBUG == factory.level

    inline fun debug(msg: () -> String) {}

    inline fun debug(format: () -> String, arguments: () -> Array<Any>) {}

    inline fun debug(msg: () -> String, t: Throwable) {}

    override val isInfoEnabled: Boolean = factory.level.isLoggable(Level.INFO)

    inline fun info(msg: () -> String) {}

    inline fun info(format: () -> String, arguments: () -> Array<Any>) {}

    inline fun info(msg: () -> String, t: Throwable) {}

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
