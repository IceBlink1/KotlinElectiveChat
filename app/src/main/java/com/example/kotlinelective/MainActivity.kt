package com.example.kotlinelective

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinelective.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, ChatFragment.newInstance()).commit()
    }
}