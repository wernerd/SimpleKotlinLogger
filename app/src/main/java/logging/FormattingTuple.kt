package logging

/*
 * Copied from slf4j - see comment in MessageFormatting in this package.
 *
 * Created by werner on 22.11.17.
 */

/**
 * Holds the results of formatting done by [MessageFormatter].
 *
 * @author Joern Huxhorn
 */
class FormattingTuple internal constructor(val message: String, val argArray: Array<Any?>? = null, val throwable: Throwable? = null)
