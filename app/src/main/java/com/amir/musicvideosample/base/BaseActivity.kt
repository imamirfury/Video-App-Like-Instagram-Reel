package com.amir.musicvideosample.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        onPreCreate()
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView(this, layoutRes)
        setSupportActionBar(getToolbar(binding as B))
        onActivityReady(savedInstanceState, binding)
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int
    open fun onPreCreate() {}
    protected abstract fun getToolbar(binding: B): Toolbar?
    protected abstract fun onActivityReady(instanceState: Bundle?, binding: B)


    fun enableBack() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    fun setToolbarTitle(title: String?) {
        supportActionBar?.let {
            it.title = title
        }
    }

    fun setToolbarTitleDisable() {
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}