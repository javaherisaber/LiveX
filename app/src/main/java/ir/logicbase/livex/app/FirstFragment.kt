package ir.logicbase.livex.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ir.logicbase.livex.app.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentFirstBinding>(
            inflater, R.layout.fragment_first, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first_navigate).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        with (requireContext()) {
            observeLiveValue(viewLifecycleOwner, viewModel.liveData, textView_first_liveData, R.string.liveData_label)
            observeLiveValue(viewLifecycleOwner, viewModel.liveEvent, textView_first_liveEvent, R.string.liveEvent_label)
            observeLiveValue(viewLifecycleOwner, viewModel.oneShotLiveEvent, textView_first_oneShotLiveEvent, R.string.oneShotLiveEvent_label)
            observeLiveValue(viewLifecycleOwner, viewModel.singleLiveEvent, textView_first_singleLiveEvent, R.string.singleLiveEvent_label)
        }
    }
}