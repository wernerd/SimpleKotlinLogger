package com.example.werner.logger

import android.content.Context
import android.util.Log
import logging.Level
import logging.LoggerAppender
import java.text.SimpleDateFormat
import java.util.*

/**
 * A log appender that stores log messages in a list up to a maximum number of messages.
 *
 * Created by werner on 15.11.17.
 */

class ListLogAppender : LoggerAppender {

    val logMessages: MutableList<String>
        get() = ArrayList(mLogList)

    override fun append(name: String, level: Level, message: String, throwable: Throwable?) {
        if (!BuildConfig.DEBUG) {
            return
        }
        val prefs = LoggerApp.appContext().getSharedPreferences("LOGGING", Context.MODE_PRIVATE)
        val logSet = prefs.getStringSet("DATA", null)

        synchronized(this) {
            if (logSet != null) {
                mLogList.clear()
                mLogList.addAll(logSet)
            }

            val time = timeFormat.format(System.currentTimeMillis()) + " "
            val logMessage = if (throwable == null) {
                time + message
            } else {
                time + message + '\n' + Log.getStackTraceString(throwable)
            }
            if (mLogList.size >= MAX_MESSAGES_IN_LIST) {
                mLogList.removeAt(0)
            }
            mLogList.add(logMessage)

            val logSetNew = LinkedHashSet<String>(mLogList.size + 10)
            logSetNew.addAll(mLogList)
            prefs.edit().putStringSet("DATA", logSetNew).apply()
        }
    }

    companion object {

        private val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        private val MAX_MESSAGES_IN_LIST = 200
        private val mLogList = ArrayList<String>(MAX_MESSAGES_IN_LIST + 1)
    }
}
