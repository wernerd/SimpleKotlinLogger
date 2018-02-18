/*
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/* Class copied from slf4j
 *
 * This logger does not use the full slf4j functionality, just this class
 * and the tuple helper class.
 *
 * Created/modified by werner on 22.11.17.
 */
package logging

// contributors: lizongbo: proposed special treatment of array parameter values
// Joern Huxhorn: pointed out double[] omission, suggested deep array copy

import java.util.HashMap

/**
 * Formats messages according to very simple substitution rules. Substitutions
 * can be made 1, 2 or more arguments.
 *
 *
 *
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;)
 * </pre>
 *
 * will return the string "Hi there.".
 *
 *
 * The {} pair is called the *formatting anchor*. It serves to designate
 * the location where arguments need to be substituted within the message
 * pattern.
 *
 *
 * In case your message contains the '{' or the '}' character, you do not have
 * to do anything special unless the '}' character immediately follows '{'. For
 * example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set {1,2,3} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {1,2,3} is not equal to 1,2.".
 *
 *
 *
 * If for whatever reason you need to place the string "{}" in the message
 * without its *formatting anchor* meaning, then you need to escape the
 * '{' character with '\', that is the backslash character. Only the '{'
 * character should be escaped. There is no need to escape the '}' character.
 * For example,
 *
 * <pre>
 * MessageFormatter.format(&quot;Set \\{} is not equal to {}.&quot;, &quot;1,2&quot;);
 * </pre>
 *
 * will return the string "Set {} is not equal to 1,2.".
 *
 *
 *
 * The escaping behavior just described can be overridden by escaping the escape
 * character '\'. Calling
 *
 * <pre>
 * MessageFormatter.format(&quot;File name is C:\\\\{}.&quot;, &quot;file.zip&quot;);
 *</pre>
 *
 * will return the string "File name is C:\file.zip".
 *
 *
 *
 * The formatting conventions are different than those of MessageFormat
 * which ships with the Java platform. This is justified by the fact that
 * SLF4J's implementation is 10 times faster than that of MessageFormat.
 * This local performance difference is both measurable and significant in the
 * larger context of the complete logging processing chain.
 *
 *
 *
 * See also [.format],
 * [.format] and
 * [.arrayFormat] methods for more details.
 *
 * @author Ceki Glc
 * @author Joern Huxhorn
 */
object MessageFormatter {
    private const val DELIM_START = '{'
    private const val DELIM_STR = "{}"
    private const val ESCAPE_CHAR = '\\'

    /**
     * Performs single argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}.&quot;, &quot;there&quot;);
     * </pre>
     *
     * will return the string "Hi there.".
     *
     *
     *
     * @param messagePattern
     * The message pattern which will be parsed and formatted
     * @param arg
     * The argument to be substituted in place of the formatting anchor
     * @return The formatted message
     */
    fun format(messagePattern: String, arg: Any): FormattingTuple =
            arrayFormat(messagePattern, arrayOf(arg))

    /**
     *
     * Performs a two argument substitution for the 'messagePattern' passed as
     * parameter.
     *
     *
     * For example,
     *
     * <pre>
     * MessageFormatter.format(&quot;Hi {}. My name is {}.&quot;, &quot;Alice&quot;, &quot;Bob&quot;);
    </pre> *
     *
     * will return the string "Hi Alice. My name is Bob.".
     *
     * @param messagePattern
     * The message pattern which will be parsed and formatted
     * @param arg1
     * The argument to be substituted in place of the first formatting
     * anchor
     * @param arg2
     * The argument to be substituted in place of the second formatting
     * anchor
     * @return The formatted message
     */
    fun format(messagePattern: String, arg1: Any, arg2: Any): FormattingTuple =
            arrayFormat(messagePattern, arrayOf(arg1, arg2))


    private fun getThrowableCandidate(argArray: Array<Any?>): Throwable? {
        if (argArray.isEmpty()) {
            return null
        }

        val lastEntry = argArray[argArray.size - 1]
        return lastEntry as? Throwable
    }

    fun arrayFormat(messagePattern: String, argArray: Array<Any?>): FormattingTuple {
        val throwableCandidate = getThrowableCandidate(argArray)
        val args = if (throwableCandidate == null) argArray else trimmedCopy(argArray)
        return arrayFormat(messagePattern, args, throwableCandidate)
    }

    private fun trimmedCopy(argArray: Array<Any?>): Array<Any?> {
        if (argArray.isEmpty()) {
            throw IllegalStateException("non-sensible empty or null argument array")
        }
        val trimmedLen = argArray.size - 1
        val trimmed = arrayOfNulls<Any>(trimmedLen)
        System.arraycopy(argArray, 0, trimmed, 0, trimmedLen)
        return trimmed
    }

    private fun arrayFormat(messagePattern: String, argArray: Array<Any?>, throwable: Throwable?): FormattingTuple {

        var i = 0
        // use string builder for better multicore performance
        val sbuf = StringBuilder(messagePattern.length + 50)

        var l = 0
        while (l < argArray.size) {

            val j = messagePattern.indexOf(DELIM_STR, i)

            if (j == -1) {
                // no more variables
                return if (i == 0) { // this is a simple string
                    FormattingTuple(messagePattern, argArray, throwable)
                } else { // add the tail string which contains no variables and return
                    // the result.
                    sbuf.append(messagePattern, i, messagePattern.length)
                    FormattingTuple(sbuf.toString(), argArray, throwable)
                }
            } else {
                if (isEscapedDelimiter(messagePattern, j)) {
                    i = if (!isDoubleEscaped(messagePattern, j)) {
                        l-- // DELIM_START was escaped, thus should not be incremented
                        sbuf.append(messagePattern, i, j - 1)
                        sbuf.append(DELIM_START)
                        j + 1
                    } else {
                        // The escape character preceding the delimiter start is
                        // itself escaped: "abc x:\\{}"
                        // we have to consume one backward slash
                        sbuf.append(messagePattern, i, j - 1)
                        deeplyAppendParameter(sbuf, argArray[l], HashMap())
                        j + 2
                    }
                } else {
                    // normal case
                    sbuf.append(messagePattern, i, j)
                    deeplyAppendParameter(sbuf, argArray[l], HashMap())
                    i = j + 2
                }
            }
            l++
        }
        // append the characters following the last {} pair.
        sbuf.append(messagePattern, i, messagePattern.length)
        return FormattingTuple(sbuf.toString(), argArray, throwable)
    }

    private fun isEscapedDelimiter(messagePattern: String, delimiterStartIndex: Int): Boolean {

        if (delimiterStartIndex == 0) {
            return false
        }
        val potentialEscape = messagePattern[delimiterStartIndex - 1]
        return potentialEscape == ESCAPE_CHAR
    }

    private fun isDoubleEscaped(messagePattern: String, delimiterStartIndex: Int): Boolean =
            delimiterStartIndex >= 2 && messagePattern[delimiterStartIndex - 2] == ESCAPE_CHAR

    // special treatment of array values was suggested by 'lizongbo'
    @Suppress("UNCHECKED_CAST")
    private fun deeplyAppendParameter(sbuf: StringBuilder, o: Any?, seenMap: MutableMap<Array<Any?>, Any?>) {
        if (o == null) {
            sbuf.append("null")
            return
        }
        if (!o.javaClass.isArray) {
            safeObjectAppend(sbuf, o)
        } else {
            // check for primitive array types because they
            // unfortunately cannot be cast to Object[]
            when (o) {
                is BooleanArray -> booleanArrayAppend(sbuf, o )
                is ByteArray -> byteArrayAppend(sbuf, o)
                is CharArray -> charArrayAppend(sbuf, o)
                is ShortArray -> shortArrayAppend(sbuf, o)
                is IntArray -> intArrayAppend(sbuf, o)
                is LongArray -> longArrayAppend(sbuf, o)
                is FloatArray -> floatArrayAppend(sbuf, o)
                is DoubleArray -> doubleArrayAppend(sbuf, o)
                else -> objectArrayAppend(sbuf, o as Array<Any?>, seenMap)
            }
        }
    }

    private fun safeObjectAppend(sbuf: StringBuilder, o: Any) {
        try {
            val oAsString = o.toString()
            sbuf.append(oAsString)
        } catch (t: Throwable) {
            report("SLF4J: Failed toString() invocation on an object of type [" + o.javaClass.name + "]", t)
            sbuf.append("[FAILED toString()]")
        }

    }

    private fun report(msg: String, t: Throwable) {
        System.err.println(msg)
        System.err.println("Reported exception:")
        t.printStackTrace()
    }

    private fun objectArrayAppend(sbuf: StringBuilder, a: Array<Any?>, seenMap: MutableMap<Array<Any?>, Any?>) {
        sbuf.append('[')
        if (!seenMap.containsKey(a)) {
            seenMap.put(a, null)
            val len = a.size
            for (i in 0 until len) {
                deeplyAppendParameter(sbuf, a[i], seenMap)
                if (i != len - 1)
                    sbuf.append(", ")
            }
            // allow repeats in siblings
            seenMap.remove(a)
        } else {
            sbuf.append("...")
        }
        sbuf.append(']')
    }

    private fun booleanArrayAppend(sbuf: StringBuilder, a: BooleanArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun byteArrayAppend(sbuf: StringBuilder, a: ByteArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i].toInt())
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun charArrayAppend(sbuf: StringBuilder, a: CharArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun shortArrayAppend(sbuf: StringBuilder, a: ShortArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until a.size) {
            sbuf.append(a[i].toInt())
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun intArrayAppend(sbuf: StringBuilder, a: IntArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun longArrayAppend(sbuf: StringBuilder, a: LongArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun floatArrayAppend(sbuf: StringBuilder, a: FloatArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

    private fun doubleArrayAppend(sbuf: StringBuilder, a: DoubleArray) {
        sbuf.append('[')
        val len = a.size
        for (i in 0 until len) {
            sbuf.append(a[i])
            if (i != len - 1)
                sbuf.append(", ")
        }
        sbuf.append(']')
    }

}
