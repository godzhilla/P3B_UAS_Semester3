package com.example.p3buassemester3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.p3buassemester3.adapter.TabAdapter
import com.example.p3buassemester3.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefManager = PrefManager.getInstance(this@MainActivity)
        val isLogged = prefManager.isLoggedIn()


        if (isLogged) {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        } else {

            with(binding) {
                viewPager.adapter = TabAdapter(this@MainActivity)
                viewPager2 = viewPager

                TabLayoutMediator(tabLayout, viewPager) {
                        tab, position ->
                    tab.text = when(position) {
                        0 -> "Register"
                        1 -> "Login"
                        else -> ""
                    }
                }.attach()

                viewPager2a = viewPager2
            }

        }



    }

    companion object {
        lateinit var viewPager2a: ViewPager2
    }
}