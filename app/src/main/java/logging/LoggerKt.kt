/*
 * Modifications Copyright 2018 Werner Dittmann
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

/*
 * Copied from slf4j and modified for Kotlin
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
interface LoggerKt {

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
}
