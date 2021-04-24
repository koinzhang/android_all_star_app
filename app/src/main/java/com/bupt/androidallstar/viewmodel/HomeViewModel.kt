package com.bupt.androidallstar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bupt.androidallstar.data.AndroidLibrary
import com.bupt.androidallstar.repository.BmobRepository
import com.bupt.androidallstar.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BmobRepository) : ViewModel() {
    var libraryRecommendData = MutableLiveData<MutableList<AndroidLibrary>>()
    var librarySearchLabelData : SingleLiveEvent<MutableList<AndroidLibrary>> =SingleLiveEvent()
    var allLabelData = MutableLiveData<MutableList<String>>()

    fun getAllRecommendLibrary() {
        viewModelScope.launch {
            repository.getAllRecommendLibrary(libraryRecommendData)
        }
    }

    fun searchLabelLibrary(searchValue: String) {
        viewModelScope.launch {
            repository.searchLabelLibrary(searchValue, librarySearchLabelData)
        }
    }

    fun getAllLabel() {
        viewModelScope.launch {
            repository.getAllLabel(allLabelData)
        }
    }
}

