package org.lijun.kotlin_demos.flows.eg.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import org.lijun.kotlin_demos.databinding.FragmentFlowRoomBinding
import org.lijun.kotlin_demos.flows.eg.adapter.UserAdapter
import org.lijun.kotlin_demos.flows.eg.viewmodel.UserViewModel

class FlowRoomFragment : Fragment() {
    private val viewModel by viewModels<UserViewModel>()

    private val flowRoomFragmentViewBinding: FragmentFlowRoomBinding by lazy {
        FragmentFlowRoomBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return flowRoomFragmentViewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        flowRoomFragmentViewBinding.apply {
            buttonInsertUser.setOnClickListener {
                viewModel.insertUser(
                    editTextUid.text.toString(),
                    editTextName.text.toString(),
                    editTextAge.text.toString().toInt(),
                    when (flowRoomFragmentViewBinding.radioGroup.checkedRadioButtonId) {
                        flowRoomFragmentViewBinding.radioButton1.id -> true
                        flowRoomFragmentViewBinding.radioButton2.id -> false
                        else -> {
                            true
                        }
                    }
                )
            }
        }
        context?.let {
            val adapter = UserAdapter(it)
            flowRoomFragmentViewBinding.recyclerviewUserInfo.adapter = adapter
            lifecycleScope.launchWhenCreated {
                viewModel.getAll().collect { value ->
                    Log.d("FlowRoom", "$value")
                    adapter.setData(value)
                }
            }
        }
    }
}