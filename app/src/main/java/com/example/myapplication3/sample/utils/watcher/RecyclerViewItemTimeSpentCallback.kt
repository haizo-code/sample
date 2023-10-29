package com.example.myapplication3.sample.utils.watcher

/**
 * @author Farouq Al-Afghani
 */
interface RecyclerViewItemTimeSpentCallback {

    /**
     * This unique identifier will be used to know where to append the new spent time to trigger the @see [onNewTimeSpent]
     */
    fun getItemUniqueIdentifier(): String

    /**
     * Will be triggered every 100 seconds while the view is still focused
     * @return total spent time on the view
     */
    fun onNewTimeSpent(totalTimeSpentInMillis: Long)
}