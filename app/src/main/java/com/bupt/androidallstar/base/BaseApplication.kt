package com.bupt.androidallstar.base

import android.app.Application
import cn.bmob.v3.Bmob
import com.bupt.androidallstar.BuildConfig
import com.bupt.androidallstar.utils.Constant
import timber.log.Timber

/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化Bmob
        Bmob.initialize(this, Constant.BMOB_APP_ID)
        //初始化Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}