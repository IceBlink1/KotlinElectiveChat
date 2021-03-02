package com.example.kotlinelective

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinelective.databinding.ChatItemBinding
import kotlinx.android.synthetic.main.chat_item.view.*

class ChatRecyclerAdapter(val list: List<Content>) :
    RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatItemBinding.inflate(inflater, parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ChatViewHolder(val binding: ChatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Content) {
            with(binding) {
                when (message) {
                    is Connected -> {
                        messageTextView.text = "User ${message.id} connected."
                        messageTextView.gravity = Gravity.CENTER_HORIZONTAL
                    }
                    is Disconnected -> {
                        messageTextView.text = "User ${message.id} disconnected."
                        messageTextView.gravity = Gravity.CENTER_HORIZONTAL
                    }
                    is Message -> {
                        messageTextView.text = "User ${message.senderId}: ${message.message}"
                    }
                }
            }
        }
    }

}