package logging

interface LoggerAppender {
    fun append(name: String, level: Level, message: String, throwable: Throwable?)
}