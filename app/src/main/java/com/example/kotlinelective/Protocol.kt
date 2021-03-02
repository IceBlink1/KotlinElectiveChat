package com.example.kotlinelective

sealed class Content

data class Message(val senderId: Int, val message: String) : Content()

data class Connected(val id: Int) : Content()

data class Disconnected(val id: Int) : Content()