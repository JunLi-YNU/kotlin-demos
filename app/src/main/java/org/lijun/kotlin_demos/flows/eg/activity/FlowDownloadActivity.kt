package org.lijun.kotlin_demos.flows.eg.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.lijun.kotlin_demos.databinding.ActivityFlowDownloadBinding

class FlowDownloadActivity : AppCompatActivity() {

    private val activityFlowDownloadBinding: ActivityFlowDownloadBinding by lazy {
        ActivityFlowDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityFlowDownloadBinding.root)
    }
}