package com.gg.leakwatcher

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.lang.ref.ReferenceQueue
import java.util.UUID
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.log

/**
 * @description:
 * @author: GG
 * @createDate: 2023 2.22 0022 15:39
 * @updateUser:
 * @updateDate: 2023 2.22 0022 15:39
 */
object Watcher {

    private val queue: ReferenceQueue<Any?> by lazy { ReferenceQueue<Any?>() }

    private val handlerThread: HandlerThread by lazy { HandlerThread("Watcher") }

    private val backgroundHandler: Handler by lazy { Handler(handlerThread.looper) }

    private val retainedKeys: CopyOnWriteArraySet<String> by lazy { CopyOnWriteArraySet() }


    fun addWatch(any: Any) {
        // 其实我们需要用 WeakReference 把对象包裹起来
        val key = UUID.randomUUID().toString()
        // 这样写只能检测到当时的情况，5 个 Activity ，当一个一个返回的时候
        val weakReference = KeyWeakReference(key, any, queue)
        retainedKeys.add(key)
        // 把这些对象存到一个集合

        // 用另外一个线程去检测
        backgroundHandler.postDelayed({
            checkRelease(weakReference)
        }, 5000)
    }

    private fun checkRelease(weakReference: KeyWeakReference) {
        removeWeaklyReferences()
        Log.e("TAG", "--->checkRelease")
        if (isRelease(weakReference)) {
            return
        }
        //手动调用GC
        runGC()

        removeWeaklyReferences()

        if (isRelease(weakReference)) {
            //会打印4次，并不是有四个对象泄露
            Log.e("TAG", "--->有内存泄漏${weakReference.get()}")
        }
    }

    private fun runGC() {
        Runtime.getRuntime().gc()
        System.runFinalization()
    }

    private fun isRelease(weakReference: KeyWeakReference): Boolean {
        return !retainedKeys.contains(weakReference.key)
    }

    private fun removeWeaklyReferences() {
        //不一定准，某些手机这里永远为空，需要再次改造
        val reference = queue.poll() as KeyWeakReference?
        while (reference != null) {
            retainedKeys.remove(reference.key)
        }
    }

    init {
        handlerThread.start()
    }
}