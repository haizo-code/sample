package com.example.myapplication3.sample.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Base [LifecycleObserver]
 */
abstract class BaseLifecycleObserver : LifecycleEventObserver {
    private var mLifecycle: Lifecycle? = null

    ////////////////////////////////////////////////
    /////// Lifecycle Observing and handling ///////
    ////////////////////////////////////////////////

    /**
     * Sets the lifecycle owner for this view. This means you don't need
     * to call **onPause()**, **onResume()** at all.
     *
     * @param ignoreUsingViewLifecycleOwner true to use the Fragment's [LifecycleOwner] rather than using
     * the Fragment's view [LifecycleOwner], false to use the Fragment's view [LifecycleOwner] if possible
     */
    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner,
        ignoreUsingViewLifecycleOwner: Boolean = true): Lifecycle {
        mLifecycle?.removeObserver(this)
        mLifecycle = lifecycleOwner.run {
            if (ignoreUsingViewLifecycleOwner) this else getProperLifecycleOwner(this)
        }.lifecycle
        mLifecycle?.apply {
            // When this method called, it will trigger the proper state method like onCreate() or onResume()
            addObserver(this@BaseLifecycleObserver)
        }
        return mLifecycle!!
    }

    private fun getProperLifecycleOwner(owner: LifecycleOwner): LifecycleOwner {
        return if (owner is Fragment) owner.viewLifecycleOwner else owner
    }

    fun isStateEquals(state: State) = mLifecycle?.currentState == state

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate()
            Lifecycle.Event.ON_START -> onStart()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_PAUSE -> onPause()
            Lifecycle.Event.ON_STOP -> onStop()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            else -> return
        }
    }

    /**
     * Notifies that {@code ON_CREATE} event occurred.
     *
     * This method will be called after the {@link LifecycleOwner}'s {@code onCreate}
     * method returns.
     */
    protected open fun onCreate() {
    }

    /**
     * Notifies that {@code ON_START} event occurred.
     *
     * This method will be called after the {@link LifecycleOwner}'s {@code onStart} method returns.
     */
    protected open fun onStart() {
    }

    /**
     * Notifies that {@code ON_RESUME} event occurred.
     *
     * This method will be called after the {@link LifecycleOwner}'s {@code onResume}
     * method returns.
     */
    protected open fun onResume() {
    }

    /**
     * Notifies that {@code ON_PAUSE} event occurred.
     *
     * This method will be called before the {@link LifecycleOwner}'s {@code onPause} method
     * is called.
     */
    protected open fun onPause() {
    }

    /**
     * Notifies that {@code ON_STOP} event occurred.
     *
     * This method will be called before the {@link LifecycleOwner}'s {@code onStop} method
     * is called.
     */
    protected open fun onStop() {
    }

    /**
     * Notifies that {@code ON_DESTROY} event occurred.
     *
     * This method will be called before the {@link LifecycleOwner}'s {@code onDestroy} method
     * is called.
     */
    protected open fun onDestroy() {
    }
}