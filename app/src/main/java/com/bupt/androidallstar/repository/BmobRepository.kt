package com.bupt.androidallstar.repository

import androidx.lifecycle.MutableLiveData
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bupt.androidallstar.data.AndroidLibrary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class BmobRepository {
    /**
     * 获取Bmob中所有推荐开源项目
     */
    suspend fun getAllRecommendLibrary(libraryRecommendData: MutableLiveData<MutableList<AndroidLibrary>>) {
        return withContext(Dispatchers.IO) {
            val bombQuery: BmobQuery<AndroidLibrary> = BmobQuery()
            bombQuery.findObjects(object : FindListener<AndroidLibrary>() {
                override fun done(data: MutableList<AndroidLibrary>?, ex: BmobException?) {
                    if (ex == null) {
                        Timber.d("Bmob find success")
                        libraryRecommendData.value = data!!
                    } else {
                        Timber.d("Bmob exception $ex")
                    }
                }
            })
        }
    }
}