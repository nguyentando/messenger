package com.donguyen.messenger.util.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.donguyen.messenger.MessengerApplication
import com.donguyen.messenger.di.component.BaseComponent

/**
 * Created by DoNguyen on 23/10/18.
 */
val Context.irisApplication: MessengerApplication
    get() = applicationContext as MessengerApplication

val Context.baseComponent: BaseComponent
    get() = irisApplication.getBaseComponent()

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(this, text, duration).show()
}

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    return Toast.makeText(this, resId, duration).show()
}