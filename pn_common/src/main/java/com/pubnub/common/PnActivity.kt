package com.pubnub.common

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class PnActivity : AppCompatActivity() {

    val channel = "acommonname"

    abstract fun attachListener()
    abstract fun initPubNub()
    abstract fun subscribe()
    abstract fun onMessageClick()
    abstract fun onSignalClick()
    abstract fun provideVersion(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initPubNub()
        pn_version.text = "PubNub version: ${provideVersion()}"
        attachListener()
        subscribe()
    }

    private fun initViews() {
        btn_message.setOnClickListener { onMessageClick() }
        btn_signal.setOnClickListener { onSignalClick() }
        tv_log.isClickable = true
        tv_log.setOnClickListener { clearLog() }
    }

    fun appendLog(message: String) {
        runOnUiThread {
            tv_log.append(message)
            tv_log.append("\n-----\n")
            log_scrollview.post { log_scrollview.fullScroll(View.FOCUS_DOWN) }
        }
    }

    private fun clearLog() {
        tv_log.text = ""
    }

}