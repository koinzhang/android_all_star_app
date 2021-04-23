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
            //开启缓存查询
            bombQuery.cachePolicy = BmobQuery.CachePolicy.CACHE_ELSE_NETWORK;
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

    /**
     * 获取Bmob中所有推荐开源项目
     */
    suspend fun searchLabelLibrary(
        searchValue: String,
        librarySearchData: MutableLiveData<MutableList<AndroidLibrary>>
    ) {
        return withContext(Dispatchers.IO) {
            val bombQuery1: BmobQuery<AndroidLibrary> = BmobQuery()
            bombQuery1.addWhereEqualTo("kindFirst", searchValue)
            val bombQuery2: BmobQuery<AndroidLibrary> = BmobQuery()
            bombQuery2.addWhereEqualTo("kindSecond", searchValue)
            val bmobQueryList = ArrayList<BmobQuery<AndroidLibrary>>()
            bmobQueryList.add(bombQuery1)
            bmobQueryList.add(bombQuery2)
            val mainQuery: BmobQuery<AndroidLibrary> = BmobQuery<AndroidLibrary>()
            val query: BmobQuery<AndroidLibrary> = mainQuery.or(bmobQueryList)
            //开启缓存查询
            query.cachePolicy = BmobQuery.CachePolicy.CACHE_ELSE_NETWORK;
            query.findObjects(object : FindListener<AndroidLibrary>() {
                override fun done(data: MutableList<AndroidLibrary>?, ex: BmobException?) {
                    if (ex == null) {
                        Timber.d("Bmob find success")
                        librarySearchData.value = data
                    } else {
                        Timber.d("Bmob exception $ex")
                    }
                }
            })
        }
    }
}