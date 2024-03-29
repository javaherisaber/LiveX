package ir.logicbase.livex

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Multiple observers can register, each one receive event only once
 */
open class LiveEvent<T> : MediatorLiveData<T>() {

    private val observers = hashSetOf<ObserverWrapper<in T>>()
    private var lastValue: T? = null
    private var hasAwaitingValue: Boolean = false

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        if (hasAwaitingValue && observers.none { it.observer == observer }) {
            observers.add(wrapper)
            this.value = lastValue
            lastValue = null
            hasAwaitingValue = false
        } else {
            observers.add(wrapper)
        }
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        if (observers.isEmpty() && LiveX.noLateObserveAllowed) {
            throw IllegalStateException("Before triggering event make sure your observers are registered in view layer")
        }
        if (observers.isEmpty()) {
            lastValue = t
            hasAwaitingValue = true
        }
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}