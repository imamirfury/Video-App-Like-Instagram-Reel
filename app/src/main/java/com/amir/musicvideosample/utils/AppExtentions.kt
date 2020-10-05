package com.amir.musicvideosample.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.amir.musicvideosample.R
import kotlinx.coroutines.*


fun <T> lazyDeferred(block : suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY){
            block.invoke(this)
        }
    }
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(): T {
    return return ViewModelProviders.of(this)[T::class.java]
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show()
}

fun Any.printDebugLog(tag: String, message: String?) {
    Log.d(tag, message.toString())
}

fun AppCompatActivity.addFragment(layoutId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(layoutId, fragment).commit()
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun AppCompatTextView.setTextOrHide(value: String? = null) {
    if (!value.isNullOrBlank()) {
        text = value
        isVisible = true
    } else {
        isVisible = false
    }
}

fun setLoadingDrawable(view: ImageView?){
    view?.let {
        view.setImageDrawable(ContextCompat.getDrawable(view.context,R.drawable.custom_progressbar))
    }
}