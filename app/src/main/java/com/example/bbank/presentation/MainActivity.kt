package com.example.bbank.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bbank.R
import com.example.bbank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}