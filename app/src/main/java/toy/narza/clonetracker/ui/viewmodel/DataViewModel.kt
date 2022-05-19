package toy.narza.clonetracker.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.repositories.RoomRepository

class DataViewModel(
    private val repository: RoomRepository
): ViewModel() {
    var dataList = MutableLiveData<List<CloneData>>()

    var standardDataList = MutableLiveData<List<CloneData>>()
    var ladderDataList = MutableLiveData<List<CloneData>>()
    var errorMessage = MutableLiveData<String>()

    fun insertAll(dataList: List<CloneData>) = viewModelScope.launch {
        repository.insert(dataList)
    }

    private fun getStandardData() = viewModelScope.launch {
        val dataList = repository.getStandard()
        standardDataList.postValue(dataList)
    }

    private fun getLadderData() = viewModelScope.launch {
        val dataList = repository.getStandard()
        ladderDataList.postValue(dataList)
    }
}