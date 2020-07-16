package ir.logicbase.livex.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.logicbase.livex.LiveEvent
import ir.logicbase.livex.OneShotLiveEvent
import ir.logicbase.livex.SingleLiveEvent

class MainViewModel : ViewModel() {

    private val _liveData = MutableLiveData<Int>()
    val liveData: LiveData<Int> = _liveData

    private val _liveEvent = LiveEvent<Int>()
    val liveEvent: LiveData<Int> = _liveEvent

    private val _oneShotLiveEvent = OneShotLiveEvent<Int>()
    val oneShotLiveEvent: LiveData<Int> = _oneShotLiveEvent

    private val _singleLiveEvent = SingleLiveEvent<Int>()
    val singleLiveEvent: LiveData<Int> = _singleLiveEvent

    private var count = 0

    fun call() {
        count++
        _liveData.value = count
        _liveEvent.value = count
        _oneShotLiveEvent.value = count
        _singleLiveEvent.value = count
    }
}