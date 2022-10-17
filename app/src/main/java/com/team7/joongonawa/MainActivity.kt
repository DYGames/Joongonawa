package com.team7.joongonawa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.team7.joongonawa.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame, PostProductFragment()).addToBackStack(null).commitAllowingStateLoss()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}