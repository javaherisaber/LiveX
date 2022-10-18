package ir.logicbase.livex

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Only one observer can register and receive event only once
 */
class OneShotLiveEvent<T> : MediatorLiveData<T>() {

    private lateinit var observerWrapper: ObserverWrapper<in T>
    private var lastValue: T? = null
    private var hasAwaitingValue: Boolean = false

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (::observerWrapper.isInitialized && observerWrapper.isRegistered) {
            throw IllegalStateException("Only one observer can be registered")
        }
        observerWrapper = ObserverWrapper(observer)
        observerWrapper.isRegistered = true
        if (hasAwaitingValue) {
            this.value = lastValue
            lastValue = null
            hasAwaitingValue = false
        }
        super.observe(owner, observerWrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observerWrapper.isRegistered) {
            observerWrapper.isRegistered = false
        }
        super.removeObserver(observerWrapper)
    }

    @MainThread
    override fun setValue(t: T?) {
        if (!::observerWrapper.isInitialized && LiveX.noLateObserveAllowed) {
            throw IllegalStateException("Before triggering event you must have one registered observer in view layer")
        }
        if (!::observerWrapper.isInitialized) {
            lastValue = t
            hasAwaitingValue = true
            return
        }
        observerWrapper.newValue()
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        var isRegistered = false
        private var pending = false

        override fun onChanged(t: T?) {
            if (pending && isRegistered) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}