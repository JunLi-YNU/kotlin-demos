package org.lijun.kotlin_demos.flows.sharedflow.eg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.lijun.kotlin_demos.databinding.FragmentTextBinding
import org.lijun.kotlin_demos.flows.sharedflow.eg.common.LocalEvenBus

class TextFragment : Fragment() {

    private val textFragmentBinding: FragmentTextBinding by lazy {
        FragmentTextBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return textFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            LocalEvenBus.events.collect() {
                textFragmentBinding.textviewTime.text = it.timeStamp.toString()
            }
        }
    }
}