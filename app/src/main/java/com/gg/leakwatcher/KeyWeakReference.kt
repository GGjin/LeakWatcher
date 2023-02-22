package com.gg.leakwatcher

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/**
 * @description:
 * @author: GG
 * @createDate: 2023 2.22 0022 15:47
 * @updateUser:
 * @updateDate: 2023 2.22 0022 15:47
 */
class KeyWeakReference(var key: String, referent: Any?, queue: ReferenceQueue<in Any?>?) : WeakReference<Any?>(referent, queue)