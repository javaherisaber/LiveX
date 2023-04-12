package ir.logicbase.livex

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

object LiveX {
    var noLateObserveAllowed: Boolean = false
}

/**
 * Used for cases where type is [Unit], to make calls cleaner.
 */
@MainThread
fun MutableLiveData<Unit>.call() {
    this.value = Unit
}

/**
 * Used for cases where type is [Unit] and is being called from background thread
 */
fun MutableLiveData<Unit>.postCall() {
    this.postValue(Unit)
}