package com.example.kotlinelective

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.lang.IllegalArgumentException

class ChatViewModel : ViewModel() {

    private val gson: Gson = Gson()
    private val mutableLiveData: MutableLiveData<List<Content>> = MutableLiveData()
    val liveData: LiveData<List<Content>> get() = mutableLiveData
    private lateinit var webSocket: WebSocket
    private val client = OkHttpClient()

    companion object {
        private const val WEB_SOCKET_URL = "ws://10.0.2.2:8885"
        private const val NORMAL_CLOSURE_STATUS = 1000
        private const val TAG = "ChatViewModel"
    }

    init {
        val request = Request.Builder().url(WEB_SOCKET_URL).build()
        webSocket = client.newWebSocket(
            request,
            ChatWebSocketListener()
        )
    }

    fun send(text: String) {
        webSocket.send(text)
    }

    inner class ChatWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.d(TAG, "onOpen response ${response.isSuccessful}")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val content = gson.fromJson(text, Map::class.java)
            val message = when (content["type"]) {
                "Connected" -> gson.fromJson(text, Connected::class.java)
                "Disconnected" -> gson.fromJson(text, Disconnected::class.java)
                "Message" -> gson.fromJson(text, Message::class.java)
                else -> throw IllegalArgumentException("Unknown message type")
            }
            mutableLiveData.postValue(mutableLiveData.value?.plus(message) ?: listOf(message))
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, reason)
            webSocket.cancel()
        }

    }

}