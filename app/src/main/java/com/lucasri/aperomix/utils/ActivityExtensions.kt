package com.lucasri.aperomix.utils

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat
import java.util.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.launchFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun Context.launchActivity(activity: AppCompatActivity) { this.startActivity(Intent(this, activity::class.java)) }

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun isTablet(context: Context): Boolean {
    return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

fun getCurrentDate(): Date {
    val currentDate = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
    return SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(currentDate)
}

fun random(min: Int, max: Int, valueToIgnore: Int): Int {
    var nbRandom: Double
    do {
        do {
            nbRandom = Math.random() * (max + 1)
        } while (nbRandom.toInt() == valueToIgnore)
    } while (nbRandom < min)

    return nbRandom.toInt()
}

fun random(min: Int, max: Int): Int {
    var nbRandom: Double
    do {
        nbRandom = Math.random() * (max + 1)
    } while (nbRandom < min)

    return nbRandom.toInt()
}

