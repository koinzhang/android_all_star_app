package com.bupt.androidallstar.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bupt.androidallstar.repository.BmobRepository
import com.bupt.androidallstar.viewmodel.HomeViewModel

/**
 * @author zhangyongkang
 * @date 2021/04/10
 * @email zyk970512@163.com
 */
class HomeViewModelFactory(var repository: BmobRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("UnKnown ViewModel Class")
    }
}