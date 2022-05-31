package toy.narza.clonetracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.repositories.RoomRepository

class DataViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    var standardDataList = MutableLiveData<List<CloneData>>()
    var ladderDataList = MutableLiveData<List<CloneData>>()
    private var thick = MutableLiveData<Long>()

    fun insertAll(dataList: List<CloneData>) = viewModelScope.launch {
        repository.insert(dataList)
    }

    fun getStandardData(): MutableLiveData<List<CloneData>> {
        viewModelScope.launch {
            standardDataList.postValue(repository.getStandard())
        }
        return standardDataList
    }

    fun getLadderData(): MutableLiveData<List<CloneData>> {
        viewModelScope.launch {
            ladderDataList.postValue(repository.getLadder())
        }
        return ladderDataList
    }

    fun insertThick(number: Long) = viewModelScope.launch {
        repository.insertThick(number)
        thick.postValue(number)
    }

    @JvmName("getThick1")
    fun getThick(): MutableLiveData<Long> {
        viewModelScope.launch {
            thick.postValue(repository.getThick())
        }
        return thick
    }
}