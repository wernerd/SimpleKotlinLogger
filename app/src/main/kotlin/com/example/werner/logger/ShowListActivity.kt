/*
 * Copyright 2018 Werner Dittmann
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

package com.example.werner.logger

import android.app.ListActivity
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter

class ShowListActivity : ListActivity() {

    // Comparator for sorting in descending order, show newest log entries first
    // Use for Android API < 24 or if you don't use jvm-target 1.8 etc
    private val compare = Comparator<String> {s1, s2 ->
        val cmp = s1.compareTo(s2)
        when {
            cmp < 0 -> 1
            cmp > 0 -> -1
            else -> 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logData = LoggerApp.listLogAppender.logMessages

        logData.sortWith(Comparator.reverseOrder())

        val head = "Commit: " + BuildConfig.BUILD_COMMIT + ", built: " + BuildConfig.BUILD_DATE

        // First String is build/commit info
        logData.add(0, head)
        val listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, logData)
        setListAdapter(listAdapter)
    }
}
