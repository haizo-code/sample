package com.example.myapplication3.sample.viewholder.stories

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowStoriesSectionLoadingBinding
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### ListItem ######################//
//####################################################//

class StoriesSectionLoadingItem : LoadingListItem {

    override val viewHolderContract: ViewHolderContract
        get() = STORIES_LOADING_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return ""
    }
}

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val STORIES_LOADING_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = StoriesLoadingViewHolder::class.java,
    layoutResId = R.layout.row_stories_section_loading,
    callbackClass = StoryActionCallback::class.java
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class StoriesLoadingViewHolder constructor(
    private val binding: RowStoriesSectionLoadingBinding
) : BaseBindingViewHolder<StoriesSectionLoadingItem>(binding) {

    override fun onBind(listItem: StoriesSectionLoadingItem) {
        binding.shimmerLayout.startShimmer()
    }
}