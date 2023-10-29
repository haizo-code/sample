package com.example.myapplication3.sample.listitem.recommended

import com.example.myapplication3.sample.model.User
import com.example.myapplication3.sample.viewholder.recommended.RECOMMENDED_FRIEND_VIEW_HOLDER_CONTRACT
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class RecommendedFriendWrapperItem constructor(
    val user: User
) : ListItemWrapper {

    override val viewHolderContract: ViewHolderContract
        get() = RECOMMENDED_FRIEND_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return user.id
    }
}