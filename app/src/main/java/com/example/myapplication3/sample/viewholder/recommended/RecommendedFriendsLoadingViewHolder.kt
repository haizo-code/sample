package com.example.myapplication3.sample.viewholder.recommended

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRecommendedFriendsSectionLoadingBinding
import com.example.myapplication3.sample.viewholder.stories.StoryActionCallback
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### ListItem ######################//
//####################################################//

class RecommendedFriendSectionLoadingItem : LoadingListItem {

    override val viewHolderContract: ViewHolderContract
        get() = RECOMMENDED_FRIENDS_LOADING_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return ""
    }
}

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val RECOMMENDED_FRIENDS_LOADING_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RecommendedFriendsLoadingViewHolder::class.java,
    layoutResId = R.layout.row_recommended_friends_section_loading,
    callbackClass = StoryActionCallback::class.java
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RecommendedFriendsLoadingViewHolder constructor(
    private val binding: RowRecommendedFriendsSectionLoadingBinding
) : BaseBindingViewHolder<RecommendedFriendSectionLoadingItem>(binding) {

    override fun onBind(listItem: RecommendedFriendSectionLoadingItem) {
        binding.shimmerLayout.startShimmer()
    }
}