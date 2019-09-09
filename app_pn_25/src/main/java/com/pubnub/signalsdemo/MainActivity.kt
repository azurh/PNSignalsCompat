package com.pubnub.signalsdemo

import android.widget.Toast
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.PNPublishResult
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult
import com.pubnub.common.PnActivity
import java.util.*

class MainActivity : PnActivity() {

    private lateinit var pubNub: PubNub

    override fun initPubNub() {
        val pnConfig = PNConfiguration()
        pnConfig.subscribeKey = "demo"
        pnConfig.publishKey = "demo"

        pubNub = PubNub(pnConfig)
    }

    override fun attachListener() {
        pubNub.addListener(object : SubscribeCallback() {
            override fun status(pubnub: PubNub?, status: PNStatus?) {}
            override fun presence(pubnub: PubNub?, presence: PNPresenceEventResult?) {}

            override fun message(pubnub: PubNub?, message: PNMessageResult?) {
                appendLog(message.toString())
            }
        })
    }

    override fun provideVersion(): String {
        return pubNub.version
    }

    override fun subscribe() {
        pubNub.subscribe()
            .channels(listOf(channel))
            .execute()
    }

    override fun onSignalClick() {
        Toast.makeText(this, "Signals not supported!", Toast.LENGTH_LONG).show()
    }

    override fun onMessageClick() {
        pubNub.publish()
            .channel(channel)
            .message(UUID.randomUUID().toString())
            .async(object : PNCallback<PNPublishResult?>() {
                override fun onResponse(result: PNPublishResult?, status: PNStatus?) {

                }
            })
    }
}
