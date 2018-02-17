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

    override val isTraceEnabled: Boolean = factory.level == Level.ALL

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

    override fun isTraceEnabled(marker: Marker): Boolean = factory.level == Level.ALL

    override fun trace(marker: Marker, msg: String) {
        trace(msg)
    }

    override fun trace(marker: Marker, format: String, arg: Any) {
        trace(format, arg)
    }

    override fun trace(marker: Marker, format: String, arg1: Any, arg2: Any) {
        trace(format, arg1, arg2)
    }

    override fun trace(marker: Marker, format: String, vararg argArray: Any) {
        trace(format, *argArray)
    }

    override fun trace(marker: Marker, msg: String, t: Throwable) {
        trace(msg, t)
    }

    override val isDebugEnabled: Boolean = Level.DEBUG == factory.level

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

    override fun isDebugEnabled(marker: Marker): Boolean = factory.level.isLoggable(Level.DEBUG)

    override fun debug(marker: Marker, msg: String) {
        debug(msg)
    }

    override fun debug(marker: Marker, format: String, arg: Any) {
        debug(format, arg)
    }

    override fun debug(marker: Marker, format: String, arg1: Any, arg2: Any) {
        debug(format, arg1, arg2)
    }

    override fun debug(marker: Marker, format: String, vararg arguments: Any) {
        debug(format, *arguments)
    }

    override fun debug(marker: Marker, msg: String, t: Throwable) {
        debug(msg, t)
    }

    override val isInfoEnabled: Boolean = factory.level.isLoggable(Level.INFO)

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

    override fun isInfoEnabled(marker: Marker): Boolean = Level.INFO.isLoggable(factory.level)

    override fun info(marker: Marker, msg: String) {
        doLogFormatted(Level.INFO, msg, null)
    }

    override fun info(marker: Marker, format: String, arg: Any) {
        doLog(Level.INFO, format, arg)
    }

    override fun info(marker: Marker, format: String, arg1: Any, arg2: Any) {
        doLog(Level.INFO, format, arg1, arg2)
    }

    override fun info(marker: Marker, format: String, vararg arguments: Any) {
        doLog(Level.INFO, format, arguments)
    }

    override fun info(marker: Marker, msg: String, t: Throwable) {
        doLogFormatted(Level.INFO, msg, t)
    }

    override val isWarnEnabled: Boolean = false

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

    override fun isWarnEnabled(marker: Marker): Boolean = Level.WARN.isLoggable(factory.level)

    override fun warn(marker: Marker, msg: String) {
        doLogFormatted(Level.WARN, msg, null)
    }

    override fun warn(marker: Marker, format: String, arg: Any) {
        doLog(Level.WARN, format, arg)
    }

    override fun warn(marker: Marker, format: String, arg1: Any, arg2: Any) {
        doLog(Level.WARN, format, arg1, arg2)
    }

    override fun warn(marker: Marker, format: String, vararg arguments: Any) {
        doLog(Level.WARN, format, arguments)
    }

    override fun warn(marker: Marker, msg: String, t: Throwable) {
        doLogFormatted(Level.WARN, msg, null)
    }

    override val isErrorEnabled: Boolean = true

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

    override fun isErrorEnabled(marker: Marker): Boolean = true

    override fun error(marker: Marker, msg: String) {
        doLogFormatted(Level.ERROR, msg, null)
    }

    override fun error(marker: Marker, format: String, arg: Any) {
        doLog(Level.ERROR, format, arg)
    }

    override fun error(marker: Marker, format: String, arg1: Any, arg2: Any) {
        doLog(Level.ERROR, format, arg1, arg2)
    }

    override fun error(marker: Marker, format: String, vararg arguments: Any) {
        doLog(Level.ERROR, format, arguments)
    }

    override fun error(marker: Marker, msg: String, t: Throwable) {
        doLogFormatted(Level.ERROR, msg, t)
    }
}
