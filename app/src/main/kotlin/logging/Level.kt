package logging

enum class Level constructor(val level: Int) {
    ALL(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4);

    /**
     * Checks if the current level can be logged
     * @param setLevel the current set level
     * @return true if logging is possible, otherwise false
     */
    fun isLoggable(setLevel: Level): Boolean = level >= setLevel.level
}
