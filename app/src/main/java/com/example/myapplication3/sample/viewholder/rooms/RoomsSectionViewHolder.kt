package com.example.myapplication3.sample.viewholder.rooms

import androidx.recyclerview.widget.PagerSnapHelper
import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRoomsSectionBinding
import com.example.myapplication3.sample.listitem.room.RoomsListContainer
import com.haizo.generaladapter.interfaces.ContainerViewHolder
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.kotlin.setupHorizontal
import com.haizo.generaladapter.listadapter.BaseRecyclerListAdapter
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val ROOMS_SECTION_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RoomsSectionViewHolder::class.java,
    layoutResId = R.layout.row_rooms_section,
    callbackClass = RoomActionCallback::class.java,
    extrasClass = RoomsViewHolderExtras::class.java
)

//####################################################//
//##################### Extras #######################//
//####################################################//

class RoomsViewHolderExtras constructor(
    val loadMoreListener: LoadMoreListener
) : ViewHolderExtras

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RoomsSectionViewHolder constructor(
    private val binding: RowRoomsSectionBinding,
    private val callback: RoomActionCallback,
    private val extras: RoomsViewHolderExtras,
) : BaseBindingViewHolder<RoomsListContainer>(binding, callback), ContainerViewHolder {

    private val adapter: BlenderListAdapter by lazy { BlenderListAdapter(context, callback) }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: RoomsListContainer) {
        if (listItem.isLoading) {
            adapter.addLoadingItem()
        } else {
            adapter.submitListItems(listItem.getInnerList(), listItem.nextPagePayload)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.setupHorizontal()
            it.adapter = adapter
            PagerSnapHelper().attachToRecyclerView(it)
        }
        adapter.setupLoadMore(loadMoreListener = extras.loadMoreListener)
        adapter.setLoadingListItem(RoomsSectionLoadingItem())
        adapter.setItemsToFitInScreen(1.2f)
    }

    override fun getInnerAdapter(): BaseRecyclerListAdapter {
        return adapter
    }
}