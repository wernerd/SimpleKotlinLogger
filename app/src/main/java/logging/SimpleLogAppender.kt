package logging

/**
 * Simple logger just for unit testing
 */
class SimpleLogAppender : LoggerAppender {
    override fun append(name: String, level: Level, message: String, throwable: Throwable?) {
        println(level.toString() + " " + name + " --> " + message)
        throwable?.printStackTrace()
        println()
    }
}
