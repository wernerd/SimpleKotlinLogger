# Package logging

This package implements a Kotlin logger that also supports SLF4J formatting.

The implementation provides function to use a SLF4J formatting to format logging data. For Kotlin 
this may not be as important as for Java because Kotlin support some nice string formatting features.
However, to simplify the transition the SLF4J formatting is quite nice.

The Logger framework itself supports log 'appender', that is each log message is handed over
to an appender which then processes the message. The package includes two appender in the
logging package and another one in the example code:

* A simple appender which prints out the formatted message on standard output using println
* The Android appender output the message using the appropriate Android Log.* functions

As an example how to implement an own appender the example code contains `ListLogAppender`, a simple
implementation that stores the latest log messages in a list. An small list activity shows how to
use this list and to see logs in an application view without using logcat. Application may add 
an appender for example to forward log message to analytic functions or alike.

The application can add or remove an appender at any time.


## Global and/or local logger ##

An application can instantiate many `LoggerFactory` objects and configure them as needed. Usually
an application just initializes a global `LoggerFactory` object and makes it available to other
classes. Because such a global `LoggerFactory` instance is available to any other module each module
can re-configure it, for example setting the log level. If a class needs a specific setup of the
logger it should instantiate its own `LoggerFactory` and use it and not change the global instance.


## The logging functions ##

The logger has the usual loggeing functions, such as `debug`, `warning` etc. Each functions comes 
in two flavours. The first one just accepts a lambda as its parameter and the lambda must return
a string. This is probably the most used variant:

    inline fun debug(msg: () -> String) {
        doLogFormatted(Level.DEBUG, msg(), null)
    }

Standard usage:

    val LOG = LoggerApp.logger.getLoggerKt(this::class.java)
    ...
    LOG.debug {"This is a simple string"}
    ...
    universalAnswer = 42
    LOG.debug {"A string using Kotlin format $this and $universalAnswer"}
    
The second debug call uses the build-in Kotlin string formatting which may replace the SLF4J
formatting in many cases.

The second variant of the logger functions accept a second parameter, a lambda that returns an 
array of `Any`. This second variant covers the SLF4J formatting that requires a number of values
that the formatter uses to setup the logging message

    inline fun debug(format: () -> String, arguments: () -> Array<Any>) {
        doLog(Level.DEBUG, format(), arguments())
    }

The typical example is:

     LOG.debug ({"A slf4j format {} and {}"}, {arrayOf(this, universalAnswer)})

The debug logging call shows the same values as the string formatted with Kotlin.
 
Using Kotlin formatting may produces less overhead. The Kotlin compiler produces a sequence of 
`StringBuilder.append` calls the create the formatted string, the SLF4J formatter is more complex.


## Setup of the logger in Android ##

The most simple way to setup the logger is to create an application class which inherits from
Android's Application. In the companion object of that class just initialize a `val` with the 
`LoggerFactory` and in `onCreate()` configure the logger for Android. Example code:

    class LoggerApp : Application() {
    
        override fun onCreate() {
            super.onCreate()
            INSTANCE = this
            logger.android()
        }
    
        companion object {
            private lateinit var INSTANCE: LoggerApp
            fun appContext() = INSTANCE.applicationContext
    
            val logger = LoggerFactory()
        }
    }

Now each other class can get the application's global logger and use it, for example in an
Android Activity:

    class LoggerActivity : AppCompatActivity() {
    
        val LOG = LoggerApp.logger.getLoggerKt(this::class.java)
    
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_logger)
            val universalAnswer = 42
            LOG.debug { "This is a simple string" }
            LOG.debug ({"A slf4j format {} and {}"}, {arrayOf(this, universalAnswer)})
            LOG.debug {"A string using Kotlin format $this and $universalAnswer"}
        }
    }

### Debug and release builds ###

For Android the logger provides two implementations, one for debug and one for release builds.
In the implementation for release builds the `debug`, `trace`, `info` functions are empty, thus the
compiler optimizes them out. Therefore debugging data such as debugging strings are removed from
release code.

The two implementations are available in `debug/java` and `release/java`respectively as required
by Android's build setup and merge rules.


## Use the logger in normal (non-Android) projects ##

In this case just use the logging package and copy one of the `LoggerImplementationKt` files into
the logging package. To get full logging use the file from the `debug` sources. 