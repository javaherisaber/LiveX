package ir.logicbase.livex

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

/**
 * Used for cases where type is [Unit], to make calls cleaner.
 */
@MainThread
fun MutableLiveData<Unit>.call() {
    this.value = Unit
}