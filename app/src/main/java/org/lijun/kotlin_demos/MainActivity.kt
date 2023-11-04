package org.lijun.kotlin_demos

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import org.lijun.kotlin_demos.coroutines.eg.activity.CoroutinesAsyncActivity
import org.lijun.kotlin_demos.coroutines.eg.activity.ExceptionHandlerActivity
import org.lijun.kotlin_demos.coroutines.eg.activity.UserInformationActivity
import org.lijun.kotlin_demos.flows.eg.activity.FlowDownloadActivity


class MainActivity : AppCompatActivity() {
    private var eventDownX = 0.0F
    private var eventDownY = 0.0F
    private var eventMoveX = 0.0F
    private var eventMoveY = 0.0F

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonCoroutinesHttp = findViewById<Button>(R.id.button_coroutines_http)
        buttonCoroutinesHttp.setOnClickListener {
            val intent = Intent(this, UserInformationActivity::class.java)
            startActivity(intent)
        }
        val buttonCoroutinesAsync = findViewById<Button>(R.id.button_coroutines_async)
        buttonCoroutinesAsync.setOnClickListener {
            val intent = Intent(this, CoroutinesAsyncActivity::class.java)
            startActivity(intent)
        }
        val buttonCaughtException = findViewById<Button>(R.id.button_caught_exception)
        buttonCaughtException.setOnClickListener {
            val intent = Intent(this, ExceptionHandlerActivity::class.java)
            startActivity(intent)
        }
        val  buttonFlow = findViewById<Button>(R.id.button_flow)
        buttonFlow.setOnClickListener {
            val intent = Intent(this, FlowDownloadActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    eventDownX = event.x
                }

                MotionEvent.ACTION_MOVE -> {
                    eventMoveX = event.x
                }

                MotionEvent.ACTION_UP -> {
                    if (eventDownX - eventMoveX > 25) {
                        startActivity(Intent(this, CoroutinesAsyncActivity::class.java))
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }
}