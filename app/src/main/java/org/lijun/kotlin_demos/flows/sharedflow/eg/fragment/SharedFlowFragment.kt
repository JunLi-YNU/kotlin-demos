package org.lijun.kotlin_demos.flows.sharedflow.eg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.lijun.kotlin_demos.databinding.FragmentSharedFlowBinding
import org.lijun.kotlin_demos.flows.sharedflow.eg.viewmodel.SharedFlowViewModel


class SharedFlowFragment : Fragment() {
    private val viewModel by viewModels<SharedFlowViewModel>()
    private val sharedFlowFragmentBinding: FragmentSharedFlowBinding by lazy {
        FragmentSharedFlowBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return sharedFlowFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedFlowFragmentBinding.apply {
            buttonStart.setOnClickListener {
                viewModel.startedRefresh()
            }
            buttonStop.setOnClickListener {
                viewModel.stopRefresh()
            }
        }
    }
}