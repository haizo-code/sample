/*
 * Copyright 2020 Farouq Afghani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haizo.generaladapter

import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BlankViewHolder
import com.haizo.generaladapter.viewholders.LoadingViewHolder

object MainViewHolderContracts {
    var NONE = ViewHolderContract(BlankViewHolder::class.java, R.layout.row_blank)
    var LOADING_VERTICAL = ViewHolderContract(LoadingViewHolder::class.java, R.layout.row_loading_vertical)
    var LOADING_HORIZONTAL = ViewHolderContract(LoadingViewHolder::class.java, R.layout.row_loading_horizontal)
}