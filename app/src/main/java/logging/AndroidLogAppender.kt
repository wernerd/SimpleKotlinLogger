package logging

import android.util.Log

/**
 * Our simple appender which logs to the android log system.
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
