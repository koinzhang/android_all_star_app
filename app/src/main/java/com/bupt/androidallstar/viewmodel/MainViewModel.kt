package com.bupt.androidallstar.viewmodel

import androidx.lifecycle.*
import com.bupt.androidallstar.data.AndroidLibrary
import com.bupt.androidallstar.database.entity.AndroidLibraryEntity
import com.bupt.androidallstar.repository.MainRepository
import com.bupt.androidallstar.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    var libraryRecommendData = MutableLiveData<MutableList<AndroidLibrary>>()
    var librarySearchLabelData: SingleLiveEvent<MutableList<AndroidLibrary>> = SingleLiveEvent()
    var allLabelData = MutableLiveData<MutableList<String>>()
    val insertFlag : SingleLiveEvent<Boolean> = SingleLiveEvent()

    val allLibrary: LiveData<List<AndroidLibraryEntity>> = repository.allLibrary.asLiveData()

    fun insert(androidLibraryEntity: AndroidLibraryEntity) = viewModelScope.launch {
        insertFlag.value = repository.insert(androidLibraryEntity)
    }

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

