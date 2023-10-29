package com.example.myapplication3.sample.viewholder.recommended

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRecommendedFriendBinding
import com.example.myapplication3.sample.listitem.recommended.RecommendedFriendWrapperItem
import com.example.myapplication3.sample.model.User
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val RECOMMENDED_FRIEND_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RecommendedFriendViewHolder::class.java,
    layoutResId = R.layout.row_recommended_friend,
    callbackClass = UserActionCallback::class.java,
)

//####################################################//
//#################### Callback ######################//
//####################################################//

interface UserActionCallback : BaseActionCallback {
    fun onUserClicked(user: User)
}

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RecommendedFriendViewHolder constructor(
    private val binding: RowRecommendedFriendBinding,
    private val callback: UserActionCallback,
) : BaseBindingViewHolder<RecommendedFriendWrapperItem>(binding, callback) {

    init {
        initListeners()
    }

    override fun onBind(listItem: RecommendedFriendWrapperItem) {
        binding.user = listItem.user
    }

    private fun initListeners() {
        itemView.setOnClickListener {
            callback.onUserClicked(listItem.user)
        }
    }
}