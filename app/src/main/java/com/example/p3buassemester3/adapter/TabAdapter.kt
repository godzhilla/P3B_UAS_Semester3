package com.example.p3buassemester3.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.p3buassemester3.logreg.LoginFragment
import com.example.p3buassemester3.logreg.RegisterFragment

class TabAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity){
    val page = arrayOf<Fragment>(RegisterFragment(), LoginFragment())
    override fun getItemCount(): Int {
        return page.size
    }

    override fun createFragment(position: Int): Fragment {
        return page[position]
    }

}