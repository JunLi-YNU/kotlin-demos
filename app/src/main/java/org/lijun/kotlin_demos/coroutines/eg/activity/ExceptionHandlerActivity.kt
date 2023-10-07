package org.lijun.kotlin_demos.coroutines.eg.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.lijun.kotlin_demos.R

class ExceptionHandlerActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception_handler)
        val exceptionHandler = CoroutineExceptionHandler { _,exception->
            Log.d("Exception","Caught $exception")
        }
        findViewById<Button>(R.id.button_exception).setOnClickListener {
            GlobalScope.launch (exceptionHandler){
                Log.d("Exception","On click")
                "LiJun".substring(10)
            }
        }
    }
}