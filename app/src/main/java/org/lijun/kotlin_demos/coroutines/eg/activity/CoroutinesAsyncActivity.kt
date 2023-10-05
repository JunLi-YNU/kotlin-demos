package org.lijun.kotlin_demos.coroutines.eg.activity

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.lijun.kotlin_demos.R


class CoroutinesAsyncActivity : AppCompatActivity() {
    private var textViewCoroutinesShow: TextView? = null

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines_demo)

        textViewCoroutinesShow = findViewById(R.id.textview_coroutines_show)
        //使用传统异步任务执行异步任务
        findViewById<Button>(R.id.button_coroutines_demo).also {
            it.setOnClickListener {
                object : AsyncTask<Void, Void, String>() {
                    @Deprecated(
                        "Deprecated in Java",
                        ReplaceWith("\"开启异步任务，获取结果\"")
                    )
                    override fun doInBackground(vararg p0: Void?): String {
                        return "使用AsyncTask开启异步任务，获取结果"
                    }

                    @Deprecated(
                        "Deprecated in Java",
                        ReplaceWith("textViewCoroutinesShow.text = result")
                    )
                    override fun onPostExecute(result: String?) {
                        textViewCoroutinesShow?.text = result
                    }

                }.execute()
            }
        }
        //使用协程执行异步任务
        findViewById<Button>(R.id.button_async_task_demo).also {
            it.setOnClickListener {
                GlobalScope.launch(Dispatchers.Main) {
                    getTextViewText()
                }
            }
        }
    }

    private suspend fun getTextViewText() {
        val str = getTextStr()
        updateTextViewShow(str)
    }

    private suspend fun getTextStr() = withContext(Dispatchers.IO) { "使用withContext开启异步任务，获取结果" }

    private fun updateTextViewShow(str: String) {
        textViewCoroutinesShow?.text = str
    }
}