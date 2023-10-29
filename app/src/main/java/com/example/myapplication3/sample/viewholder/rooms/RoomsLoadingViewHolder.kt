package com.example.myapplication3.sample.viewholder.rooms

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRoomsSectionLoadingBinding
import com.example.myapplication3.sample.viewholder.stories.StoryActionCallback
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### ListItem ######################//
//####################################################//

class RoomsSectionLoadingItem : LoadingListItem {

    override val viewHolderContract: ViewHolderContract
        get() = ROOMS_LOADING_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return ""
    }
}

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val ROOMS_LOADING_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RoomsSectionLoadingViewHolder::class.java,
    layoutResId = R.layout.row_rooms_section_loading,
    callbackClass = StoryActionCallback::class.java
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RoomsSectionLoadingViewHolder constructor(
    private val binding: RowRoomsSectionLoadingBinding
) : BaseBindingViewHolder<RoomsSectionLoadingItem>(binding) {

    override fun onBind(listItem: RoomsSectionLoadingItem) {
        binding.shimmerLayout.startShimmer()
    }
}