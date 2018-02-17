package logging

/*
 * Copied from slf4j - see comment in MessageFormatting in this package.
 *
 * Created by werner on 22.11.17.
 */

/**
 * The org.slf4j.LoggerJv interface is the main user entry point of SLF4J API.
 * It is expected that logging takes place through concrete implementations
 * of this interface.
 *
 *
 * <h3>Typical usage pattern:</h3>
 * <pre>
 * import com.comfylight.common.log.LoggerJv;
 * import org.slf4j.LoggerFactory;
 *
 * public class Wombat {
 *
 * <span style="color:green">final static LoggerJv logger = LoggerFactory.getLoggerJv(Wombat.class);</span>
 * Integer t;
 * Integer oldT;
 *
 * public void setTemperature(Integer temperature) {
 * oldT = t;
 * t = temperature;
 * <span style="color:green">logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);</span>
 * if(temperature.intValue() > 50) {
 * <span style="color:green">logger.info("Temperature has risen above 50 degrees.");</span>
 * }
 * }
 * }
 * </pre>
 *
 * Be sure to read the FAQ entry relating to [parameterized
 * logging](../../../faq.html#logging_performance). Note that logging statements can be parameterized in
 * [presence of an exception/throwable](../../../faq.html#paramException).
 *
 *
 * Once you are comfortable using loggers, i.e. instances of this interface, consider using
 * [MDC](MDC.html) as well as [Markers](Marker.html).
 *
 * @author Ceki Glc
 */
interface LoggerJv {

    /**
     * Return the name of this `LoggerJv` instance.
     * @return name of this logger instance
     */
    val name: String

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this LoggerJv is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    val isTraceEnabled: Boolean

    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this LoggerJv is enabled for the DEBUG level,
     * false otherwise.
     */
    val isDebugEnabled: Boolean

    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this LoggerJv is enabled for the INFO level,
     * false otherwise.
     */
    val isInfoEnabled: Boolean

    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this LoggerJv is enabled for the WARN level,
     * false otherwise.
     */
    val isWarnEnabled: Boolean

    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this LoggerJv is enabled for the ERROR level,
     * false otherwise.
     */
    val isErrorEnabled: Boolean

    /**
     * Log a message at the TRACE level.
     *
     * @param msg the message string to be logged
     * @since 1.4
     */
    fun trace(msg: String)

    /**
     * Log a message at the TRACE level according to the specified format
     * and argument.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     *
     * @param format the format string
     * @param arg    the argument
     * @since 1.4
     */
    fun trace(format: String, arg: Any)

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the TRACE level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     * @since 1.4
     */
    fun trace(format: String, arg1: Any, arg2: Any)

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the TRACE level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for TRACE. The variants taking [one][.trace] and
     * [two][.trace] arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    fun trace(format: String, vararg arguments: Any)

    /**
     * Log an exception (throwable) at the TRACE level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    fun trace(msg: String, t: Throwable)


    /**
     * Log a message at the DEBUG level.
     *
     * @param msg the message string to be logged
     */
    fun debug(msg: String)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and argument.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun debug(format: String, arg: Any)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun debug(format: String, arg1: Any, arg2: Any)

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * [one][.debug] and [two][.debug]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun debug(format: String, vararg arguments: Any)

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun debug(msg: String, t: Throwable)

    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    fun info(msg: String)

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun info(format: String, arg: Any)

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the INFO level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun info(format: String, arg1: Any, arg2: Any)

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * [one][.info] and [two][.info]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun info(format: String, vararg arguments: Any)

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun info(msg: String, t: Throwable)

    /**
     * Log a message at the WARN level.
     *
     * @param msg the message string to be logged
     */
    fun warn(msg: String)

    /**
     * Log a message at the WARN level according to the specified format
     * and argument.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun warn(format: String, arg: Any)

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the WARN level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for WARN. The variants taking
     * [one][.warn] and [two][.warn]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun warn(format: String, vararg arguments: Any)

    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the WARN level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun warn(format: String, arg1: Any, arg2: Any)

    /**
     * Log an exception (throwable) at the WARN level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun warn(msg: String, t: Throwable)

    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    fun error(msg: String)

    /**
     * Log a message at the ERROR level according to the specified format
     * and argument.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     *
     * @param format the format string
     * @param arg    the argument
     */
    fun error(format: String, arg: Any)

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous object creation when the logger
     * is disabled for the ERROR level.
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    fun error(format: String, arg1: Any, arg2: Any)

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     *
     * This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an `Object[]` before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * [one][.error] and [two][.error]
     * arguments exist solely in order to avoid this hidden cost.
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    fun error(format: String, vararg arguments: Any)

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    fun error(msg: String, t: Throwable)
}
