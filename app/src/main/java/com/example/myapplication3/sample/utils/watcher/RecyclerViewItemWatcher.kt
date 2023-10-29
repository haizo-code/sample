package com.example.myapplication3.sample.utils.watcher

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.postDelayed
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication3.sample.utils.BaseLifecycleObserver
import com.example.myapplication3.sample.utils.Utils
import java.util.Hashtable

/**
 * @author Farouq Al-Afghani
 */
class RecyclerViewItemWatcher<T> constructor(
    private val recyclerView: RecyclerView
) : BaseLifecycleObserver() {

    // HashMap of <UniqueID, TimeInMillis>
    private val timeSpentHashMap: HashMap<String, Long> = HashMap()

    private var focusTimeInMillisToConsiderItemAsViewed: Long = 2000

    /*list of handler tokens ids*/
    private val tokensList = HashSet<Any>()

    /*list of handler token that has been visible in the view port and its event been send*/
    private val tokensBelongToFiredEvents = HashSet<Any>()

    /*handler instance */
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    /*handler instance */
    private val timeSpentHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    /*list of handler tokens ids*/
    private val timeSpentTokens = HashSet<Any>()

    /*The interval time for capturing the view watching*/
    private val timeCapturingIntervalMillis: Long = 100

    /*Flag to know if should watch the exact time on each view or not */
    private var isWatchExactTimeOnEachView: Boolean = false

    /*Current lifecycle status; is paused or not*/
    private var isPaused: Boolean = false

    /*will be used to consider when the item is accepted as visible*/
    private var acceptedVisibilityPercentage: Float = 0.7f

    private var onNewItemViewedCallback: ((T) -> Unit)? = null

    fun initialize() {
        // Used to remove the handle event once the child view detached.
        onChildAttachStateChangeListener.let {
            recyclerView.removeOnChildAttachStateChangeListener(it)
            recyclerView.addOnChildAttachStateChangeListener(it)
        }

        // Using the onScrolled to keep the checking of view percentage visibility, its only call once in the [onChildViewAttachedToWindow]
        onScrollListener.let {
            recyclerView.removeOnScrollListener(it)
            recyclerView.addOnScrollListener(it)
        }
    }

    /**
     * @param [value] The accepted percentage to consider that this view is focused and visible
     */
    fun setItemAcceptedVisibilityPercentage(value: Float) {
        this.acceptedVisibilityPercentage = value
    }

    /**
     * @param [value] The time in millis to consider that the view is viewed (while satisfying the @see[acceptedVisibilityPercentage])
     */
    fun setFocusTimeInMillisToConsiderItemAsViewed(value: Long) {
        this.focusTimeInMillisToConsiderItemAsViewed = value
    }

    /**
     * @param [onNewItemViewedCallback] This callback will be triggered only once for each view (no repetition for the same view)
     */
    fun setOnNewItemViewedCallback(onNewItemViewedCallback: (T) -> Unit) {
        this.onNewItemViewedCallback = onNewItemViewedCallback
    }

    /**
     * @param [lifecycleOwner] will be used to detect lifeCycle changes for onResume/onPause to resume/stop the calculation for spent time
     */
    fun registerForExactSpentTimeOnEachItemView(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
        isWatchExactTimeOnEachView = true
    }

    /**
     * @return the recorded time spent map <Unique Identifier, Time in Millis>
     */
    fun getRecordedTimeSpentMap() = timeSpentHashMap

    private val onChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(child: View) {
            //please note the onChildViewAttachedToWindow is called one time only so this check will not
            //re called another time until the view detach and attached again.
            checkTimeSpentToFireEvent(child)
        }

        // Note that this is being used because this method know that the child has been deAttached before the on scroll knows
        // ex: when user scroll and the view deAttached then he stopped, the ScrollListener still thinking that its still attached
        override fun onChildViewDetachedFromWindow(child: View) {
            val token: Any = child
            //if view scrolled and detached from RV, remove the handler callback.
            // we only remove the handle token from tokensList here, to insure after user scroll in the view port
            // we don't send the event twice after it view become visible twice for this [visibilityPercentage] before its remove from recycler cache.
            removeCheckHandleCallBack(token)
            //remove event send token here.
            tokensBelongToFiredEvents.remove(token)
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            for (pos in 0 until recyclerView.childCount) {
                val child = recyclerView.getChildAt(pos)
                checkTimeSpentToFireEvent(child)
            }
        }
    }

    private fun watchAndCalculateTheSpentTimeOnView(child: View, isViewVisibleWithinPercentage: Boolean) {
        val token: Any = child
        if (isViewVisibleWithinPercentage && !isPaused) {
            timeSpentHandler.postDelayed(timeCapturingIntervalMillis, token) {
                timeSpentTokens.add(token)
                if (isViewVisibleWithinPercentage(child)) {
                    triggerOnNewTimeSpent(child, timeCapturingIntervalMillis)
                    clearTimeSpentCallback(token)
                    watchAndCalculateTheSpentTimeOnView(child, true)
                }
            }
        } else {
            clearTimeSpentCallback(token)
        }
    }

    private fun clearTimeSpentCallback(token: Any) {
        timeSpentHandler.removeCallbacksAndMessages(token)
        timeSpentTokens.clear()
    }

    private fun checkTimeSpentToFireEvent(child: View) {
        //handle token to be used.
        val token: Any = child

        //check if the view item is visible within the giving percentage.
        // and check if the token is not yet called to avoid multi call & its already not send previously in case still visible and we scroll.
        val isViewVisibleWithinPercentage = isViewVisibleWithinPercentage(child)

        triggerFocusEvent(child, isViewVisibleWithinPercentage)

        if (isWatchExactTimeOnEachView) {
            watchAndCalculateTheSpentTimeOnView(child, isViewVisibleWithinPercentage)
        }

        if (isViewVisibleWithinPercentage && !tokensList.contains(token) && !tokensBelongToFiredEvents.contains(token)) {
            getViewHolderEvent(child)?.let { postViewedEvent ->
                //add to list so we can check later if it is already added and under fire event check(waiting the handler callback).
                tokensList.add(token)

                //sleep time that view is still visible before firing the event callback
                handler.postDelayed(focusTimeInMillisToConsiderItemAsViewed, token) {
                    //always calculate the visibility
                    if (isViewVisibleWithinPercentage(child)) {
                        //fire event callback
                        postViewedEvent.getViewedItem()?.let {
                            onNewItemViewedCallback?.invoke(it)
                            // once event send add the send token to the list,
                            // so we can check to not send it before it fully removed and detached from recycler view.
                            tokensBelongToFiredEvents.add(token)
                        }
                    }
                }
            }
        } else if (!isViewVisibleWithinPercentage && tokensList.contains(token)) {
            //if the view not visible and not under event handle firing we remove the token
            //in some cases this will be called before [onChildViewDetachedFromWindow] but we keep waiting it to detached to remove it from send event list.
            removeCheckHandleCallBack(token)
        }
    }

    private fun isViewVisibleWithinPercentage(child: View): Boolean {
        return Utils.getViewVisibleAreaOffset(child) >= acceptedVisibilityPercentage
    }

    private fun removeCheckHandleCallBack(token: Any) {
        handler.removeCallbacksAndMessages(token)
        tokensList.remove(token)
    }

    private fun getViewHolderEvent(child: View): RecyclerViewItemViewCallback<T>? {
        val vh = try {
            recyclerView.getChildViewHolder(child)
        } catch (e: Exception) {
            null
        }
        @Suppress("UNCHECKED_CAST")
        return vh as? RecyclerViewItemViewCallback<T>
    }

    /**
     * This method will be called every [timeCapturingIntervalMillis]ms
     * so you need to collect the intervals as small chunks of 100ms in your ViewHolder
     */
    private fun triggerOnNewTimeSpent(child: View, @Suppress("SameParameterValue") timeAddition: Long) {
        val vh = try {
            recyclerView.getChildViewHolder(child)
        } catch (e: Exception) {
            null
        }
        (vh as? RecyclerViewItemTimeSpentCallback)?.onNewTimeSpent(
            getTotalTimeSpentForViewHolder(vh, timeAddition)
        )
    }

    private val focusItemHashSet = Hashtable<View, Boolean>()

    private fun triggerFocusEvent(child: View, isFocused: Boolean) {
        val vh = try {
            recyclerView.getChildViewHolder(child)
        } catch (e: Exception) {
            null
        }

        if (focusItemHashSet[child] != null) {
            if (focusItemHashSet.getValue(child) == isFocused) return
        }
        focusItemHashSet[child] = isFocused

        @Suppress("UNCHECKED_CAST")
        (vh as? RecyclerViewItemViewCallback<T>)?.onViewFocused(isFocused)
    }

    private fun getTotalTimeSpentForViewHolder(viewHolder: ViewHolder, timeAddition: Long): Long {
        (viewHolder as? RecyclerViewItemTimeSpentCallback)?.let {
            val itemUniqueIdentifier = it.getItemUniqueIdentifier()
            var timeSpent: Long = if (timeSpentHashMap.containsKey(itemUniqueIdentifier)) {
                timeSpentHashMap[itemUniqueIdentifier] ?: 0
            } else 0
            timeSpent += timeAddition
            timeSpentHashMap[itemUniqueIdentifier] = timeSpent
            return timeSpent
        }
        return 0
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> isPaused = true
            Lifecycle.Event.ON_RESUME -> isPaused = false
            else -> Unit
        }
    }
}