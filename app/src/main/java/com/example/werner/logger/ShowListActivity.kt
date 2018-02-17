package com.example.werner.logger

import android.app.ListActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import java.util.*

class ShowListActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logList = LoggerApp.listLogAppender
        val logData = logList.logMessages

        // Sort data, first field is time. Need to do this because persistence function uses a Set
        Collections.sort<String>(logData)

        val head = "Commit: " + BuildConfig.BUILD_COMMIT + ", built: " + BuildConfig.BUILD_DATE
        // First String is build/commit info
        //
        logData.add(0, head)
        val listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, logData)
        setListAdapter(listAdapter)
    }
}
