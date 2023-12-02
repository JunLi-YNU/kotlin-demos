package org.lijun.kotlin_demos.flows.stateflow.eg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import org.lijun.kotlin_demos.databinding.FragmentStateFlowBinding
import org.lijun.kotlin_demos.flows.stateflow.eg.viewmodel.StateFlowViewModel

class StateFlowFragment : Fragment() {

    private val viewModel by viewModels<StateFlowViewModel>()
    private val fragmentStateFlowBinding: FragmentStateFlowBinding by lazy {
        FragmentStateFlowBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return fragmentStateFlowBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragmentStateFlowBinding.apply {
            buttonIncrement.setOnClickListener() {
                viewModel.increment()
            }

            buttonDecrement.setOnClickListener() {
                viewModel.decrement()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.numberSateFlow.collect() {
                fragmentStateFlowBinding.textviewNumberShow.text = "$it"
            }
        }
    }
}