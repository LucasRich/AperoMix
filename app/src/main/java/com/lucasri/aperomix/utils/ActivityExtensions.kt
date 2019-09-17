package com.lucasri.aperomix.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lucasri.aperomix.R

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun isTablet(context: Context): Boolean {
    return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun setToolbarTitle(activity: Activity, title: String){
    val toolbar: Toolbar = activity!!.findViewById<View>(R.id.toolbar) as Toolbar
    toolbar.title = title
}