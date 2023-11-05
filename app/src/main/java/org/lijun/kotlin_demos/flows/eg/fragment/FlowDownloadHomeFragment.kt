package org.lijun.kotlin_demos.flows.eg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.lijun.kotlin_demos.R
import org.lijun.kotlin_demos.databinding.FragmentFlowDownloadHomeBinding

class FlowDownloadHomeFragment : Fragment() {
    private val fragmentFlowDownloadHomeBinding: FragmentFlowDownloadHomeBinding by lazy {
        FragmentFlowDownloadHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return fragmentFlowDownloadHomeBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentFlowDownloadHomeBinding.btnFlowDownload.setOnClickListener {
            findNavController().navigate(R.id.action_flowDownloadHomeFragment_to_flowDownLoadFragment)
        }
        fragmentFlowDownloadHomeBinding.btnFlowRoom.setOnClickListener {
            findNavController().navigate(R.id.action_flowDownloadHomeFragment_to_flowRoomFragment)
        }
        fragmentFlowDownloadHomeBinding.btnFlowRetrofit.setOnClickListener {
            findNavController().navigate(R.id.action_flowDownloadHomeFragment_to_flowRetrofitFragment)
        }
    }
}