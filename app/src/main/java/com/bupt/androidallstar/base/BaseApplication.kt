package com.bupt.androidallstar.base

import android.app.Application
import cn.bmob.v3.Bmob
import com.bupt.androidallstar.BuildConfig
import com.bupt.androidallstar.database.appdatabase.LibraryDataBase
import com.bupt.androidallstar.repository.MainRepository
import com.bupt.androidallstar.utils.Constant
import com.bupt.androidallstar.viewmodel.MainViewModel
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        //初始化Bmob
        Bmob.initialize(this, Constant.BMOB_APP_ID)
        //初始化mmkv
        val rootDir = MMKV.initialize(this)
        Timber.d("mmkv root: $rootDir")

        startKoin {
            //Android context
            androidContext(this@BaseApplication)
            //modules
            val list = listOf(myModule, repoModule)
            modules(list)
        }
    }

    private val myModule = module {
        viewModel { MainViewModel(get()) }
    }

    private val repoModule = module {
        single {
            MainRepository(
                LibraryDataBase.getDatabase(this@BaseApplication).androidLibraryDao()
            )
        }
    }
}