package org.lijun.kotlin_demos.flows.eg.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.lijun.kotlin_demos.databinding.FragmentFlowDownLoadBinding
import org.lijun.kotlin_demos.flows.eg.download.DownloadManager
import org.lijun.kotlin_demos.flows.eg.download.DownloadStatus
import java.io.File


class FlowDownLoadFragment : Fragment() {
    private val fragmentFlowDownLoadBinding: FragmentFlowDownLoadBinding by lazy {
        FragmentFlowDownLoadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return fragmentFlowDownLoadBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            context?.apply {
                val file = File(getExternalFilesDir(null)?.path, "demo.text")
                Log.d("Download", "FilePath:${file.path}")
                DownloadManager.download("https://www.baidu.com/index.html", file)
                    .collect() { status ->
                        when (status) {
                            is DownloadStatus.Progress -> {
                                fragmentFlowDownLoadBinding.apply {
                                    downloadProgressbar.progress = status.value
                                    tvProgress.text = "${status.value}"
                                }
                            }

                            is DownloadStatus.Error -> {
                                Log.e("Download", "${status.throwable}")
                                Toast.makeText(
                                    context,
                                    "Download Error:${status.throwable}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            is DownloadStatus.Done -> {
                                fragmentFlowDownLoadBinding.apply {
                                    downloadProgressbar.progress = 100
                                    tvProgress.text = "100%"
                                }
                            }

                            else -> {
                                Toast.makeText(context, "Download failed.", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}