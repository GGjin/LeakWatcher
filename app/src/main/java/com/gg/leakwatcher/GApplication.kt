package com.gg.leakwatcher

import android.app.Activity
import android.app.Application

/**
 * @description:
 * @author: GG
 * @createDate: 2023 2.22 0022 16:02
 * @updateUser:
 * @updateDate: 2023 2.22 0022 16:02
 */
class GApplication : Application() {

    private val mCurrentActivity = mutableListOf<Activity>()

    override fun onCreate() {
        super.onCreate()
        LeakCanary.init(this)
    }

    fun addCurrentActivity(activity: Activity) {
        mCurrentActivity.add(activity)
    }

    fun getCurrentActivity(): Activity = mCurrentActivity.get(0)
}