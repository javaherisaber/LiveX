package ir.logicbase.livex.app

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun Context.observeLiveValue(owner: LifecycleOwner, event: LiveData<Int>, textView: TextView, stringRes: Int) {
    try {
        textView.text = getString(stringRes, "Observer not called")
        event.observe(owner, Observer {
            textView.text = getString(stringRes, it)
        })
    } catch (e: IllegalStateException) {
        textView.text = getString(stringRes, "Not registered")
    }
}