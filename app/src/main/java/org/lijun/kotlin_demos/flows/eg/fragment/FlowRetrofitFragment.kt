package org.lijun.kotlin_demos.flows.eg.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import org.lijun.kotlin_demos.databinding.FragmentFlowRetrofitBinding
import org.lijun.kotlin_demos.flows.eg.adapter.NormalUserAdapter
import org.lijun.kotlin_demos.flows.eg.adapter.UserAdapter
import org.lijun.kotlin_demos.flows.eg.database.User
import org.lijun.kotlin_demos.flows.eg.viewmodel.FlowRetrofitNormalUserViewModel

class FlowRetrofitFragment : Fragment() {
    private val viewModel by viewModels<FlowRetrofitNormalUserViewModel>()

    private val fragmentFlowRetrofitBinding: FragmentFlowRetrofitBinding by lazy {
        FragmentFlowRetrofitBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return fragmentFlowRetrofitBinding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(State.CREATED) {
                fragmentFlowRetrofitBinding.editTextSearch.textWatcherFlow().collect() {
                    Log.d(FlowRetrofitFragment::class.toString(), "collect keywords:$it")
                    viewModel.searchNormalUserByNickName(it)
                }
            }
        }
        context?.let {
            val adapter = NormalUserAdapter(it)
            fragmentFlowRetrofitBinding.textViewSearch.adapter = adapter
            viewModel.normalUserList.observe(viewLifecycleOwner) { normalUserList ->
                adapter.setData(normalUserList)
            }
        }
    }

    private fun TextView.textWatcherFlow(): Flow<String> = callbackFlow {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("LiJun", p0.toString())
                trySend(p0.toString()).isSuccess
            }

        }
        addTextChangedListener(textWatcher)
        awaitClose() {
            removeTextChangedListener(textWatcher)
        }
    }
}
