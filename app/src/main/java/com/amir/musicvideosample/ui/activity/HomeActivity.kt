package com.amir.musicvideosample.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.amir.musicvideosample.R
import com.amir.musicvideosample.base.BaseActivity
import com.amir.musicvideosample.data.factory.ViewModelFactory
import com.amir.musicvideosample.databinding.ActivityHomeBinding
import com.amir.musicvideosample.ui.fragment.HomeFragment
import com.amir.musicvideosample.utils.addFragment
import com.amir.musicvideosample.utils.toast
import com.amir.musicvideosample.viewModel.VideosViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity<ActivityHomeBinding>(), BottomNavigationView.OnNavigationItemSelectedListener,KodeinAware {

    override val kodein: Kodein by kodein()
    override val layoutRes: Int get() = R.layout.activity_home

    override fun getToolbar(binding: ActivityHomeBinding): Toolbar?  = binding.toolbar
    override fun onActivityReady(instanceState: Bundle?, binding: ActivityHomeBinding) {
        enableBack()
        setToolbarTitleDisable()
        addFragment(R.id.homeContainer, HomeFragment())
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