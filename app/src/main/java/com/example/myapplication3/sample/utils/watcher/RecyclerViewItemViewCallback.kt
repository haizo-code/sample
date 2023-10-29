package com.example.myapplication3.sample.utils.watcher

/**
 * @author Farouq Al-Afghani
 */
interface RecyclerViewItemViewCallback<T> {

    /**
     * This item will be returned in the @see[RecyclerViewItemWatcher.setOnNewItemViewedCallback]
     */
    fun getViewedItem(): T?

    /**
     * Will be triggered when the view focus status is changed (true|false)
     */
    fun onViewFocused(isFocused: Boolean)
}