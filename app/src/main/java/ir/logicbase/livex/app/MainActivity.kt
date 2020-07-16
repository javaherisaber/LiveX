package ir.logicbase.livex.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ir.logicbase.livex.app.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        val binding = DataBindingUtil.inflate<ActivityMainBinding>(
            layoutInflater, R.layout.activity_main, null, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        observeLiveValue(this, viewModel.liveData, textView_main_liveData, R.string.liveData_label)
        observeLiveValue(this, viewModel.liveEvent, textView_main_liveEvent, R.string.liveEvent_label)
        observeLiveValue(this, viewModel.oneShotLiveEvent, textView_main_oneShotLiveEvent, R.string.oneShotLiveEvent_label)
        observeLiveValue(this, viewModel.singleLiveEvent, textView_main_singleLiveEvent, R.string.singleLiveEvent_label)
    }

}