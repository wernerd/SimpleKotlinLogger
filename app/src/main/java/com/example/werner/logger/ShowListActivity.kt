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
