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
 * Holds the results of formatting done by [MessageFormatter].
 *
 * @author Joern Huxhorn
 */
class FormattingTuple internal constructor(val message: String, val argArray: Array<Any?>? = null, val throwable: Throwable? = null)
