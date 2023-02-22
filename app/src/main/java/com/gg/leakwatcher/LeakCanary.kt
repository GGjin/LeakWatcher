package com.gg.leakwatcher

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @description:监控activity和fragment是否被释放
 * @author: GG
 * @createDate: 2023 2.22 0022 15:39
 * @updateUser:
 * @updateDate: 2023 2.22 0022 15:39
 */
object LeakCanary {

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                Watcher.addWatch(p0)
            }

        })
    }
}