package com.example.myapplication3.sample.viewholder.recommended

import com.example.myapplication3.R
import com.example.myapplication3.databinding.RowRecommendedFriendsSectionBinding
import com.example.myapplication3.sample.listitem.recommended.RecommendedFriendsListContainer
import com.haizo.generaladapter.interfaces.ContainerViewHolder
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.kotlin.setupHorizontal
import com.haizo.generaladapter.listadapter.BaseRecyclerListAdapter
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

//####################################################//
//#################### Contract ######################//
//####################################################//

internal val COMMENDED_FRIENDS_SECTION_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = RecommendedFriendsSectionViewHolder::class.java,
    layoutResId = R.layout.row_recommended_friends_section,
    callbackClass = UserActionCallback::class.java,
    extrasClass = RecommendedFriendsViewHolderExtras::class.java,
)

//####################################################//
//##################### Extras #######################//
//####################################################//

class RecommendedFriendsViewHolderExtras constructor(
    val loadMoreListener: LoadMoreListener
) : ViewHolderExtras

//####################################################//
//################## ViewHolder ######################//
//####################################################//

internal open class RecommendedFriendsSectionViewHolder constructor(
    private val binding: RowRecommendedFriendsSectionBinding,
    private val callback: UserActionCallback,
    private val extras: RecommendedFriendsViewHolderExtras
) : BaseBindingViewHolder<RecommendedFriendsListContainer>(binding, callback), ContainerViewHolder {

    private val adapter: BlenderListAdapter by lazy {
        BlenderListAdapter(context, callback)
    }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: RecommendedFriendsListContainer) {
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
        }
        adapter.setupLoadMore(loadMoreListener = extras.loadMoreListener)
        adapter.setLoadingListItem(RecommendedFriendSectionLoadingItem())
    }

    override fun getInnerAdapter(): BaseRecyclerListAdapter {
        return adapter
    }
}