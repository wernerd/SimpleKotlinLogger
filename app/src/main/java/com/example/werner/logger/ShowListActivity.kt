package com.example.werner.logger

import android.app.ListActivity
import android.os.Bundle
import android.widget.ArrayAdapter

class ShowListActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logData = LoggerApp.listLogAppender.logMessages
        // Sort data, first field is time. Need to do this because persistence function uses a Set
        logData.sort()

        val head = "Commit: " + BuildConfig.BUILD_COMMIT + ", built: " + BuildConfig.BUILD_DATE
        // First String is build/commit info
        //
        logData.add(0, head)
        val listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, logData)
        setListAdapter(listAdapter)
    }
}
