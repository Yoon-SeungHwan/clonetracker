package toy.narza.clonetracker.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import toy.narza.clonetracker.db.AppDataBase
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.viewmodel.DataViewModel


class NzReceiver : BroadcastReceiver() {
    private var viewModel: DataViewModel? = null

    override fun onReceive(context: Context?, p1: Intent?) {
        when (p1?.action) {
            NZService.ACTION_THICK -> postThick(p1)
            NZService.ACTION_DATA -> postData(p1)
        }
    }

    private fun postThick(intent: Intent?) {
        val thick = intent?.extras?.getLong(NZService.KEY_THICK_VALUE)
        thick?.let {
            viewModel?.insertThick(thick)
        }
    }

    private fun postData(intent: Intent?) {
        intent?.extras?.getSerializable(NZService.KEY_UPDATE_DB)?.let {
            viewModel?.insertAll(it as List<CloneData>)
        }
    }

    fun setViewModel(viewModel: DataViewModel) {
        this.viewModel = viewModel
    }
}