package com.bupt.androidallstar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bupt.androidallstar.data.AndroidLibrary
import com.bupt.androidallstar.repository.BmobRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BmobRepository) : ViewModel() {
    var libraryRecommendData = MutableLiveData<MutableList<AndroidLibrary>>()

    fun getAllRecommendLibrary() {
        viewModelScope.launch {
            repository.getAllRecommendLibrary(libraryRecommendData)
        }
    }
}

