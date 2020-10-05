package com.amir.musicvideosample.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.amir.musicvideosample.R
import com.amir.musicvideosample.base.BaseActivity
import com.amir.musicvideosample.databinding.ActivityHomeBinding
import com.amir.musicvideosample.ui.fragment.HomeFragment
import com.amir.musicvideosample.utils.addFragment
import com.amir.musicvideosample.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : BaseActivity<ActivityHomeBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {


    override val layoutRes: Int get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar?  = null
    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        addFragment(R.id.homeContainer,HomeFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> toast("Home Clicked")
            R.id.video -> toast("Video Clicked")
            R.id.add -> toast("Add Clicked")
            R.id.like -> toast("Like Clicked")
            R.id.profile -> toast("Profile Clicked")
        }
        return true
    }
}