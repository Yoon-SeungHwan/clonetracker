package toy.narza.clonetracker.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.ApiService
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory
import toy.narza.clonetracker.network.constants.LadderMode
import toy.narza.clonetracker.network.constants.Mode
import toy.narza.clonetracker.network.constants.Region
import toy.narza.clonetracker.utils.Utils

class NZService : Service() {
    companion object {
        const val ACTION_DATA = "toy.narza.clonetracker.service.Service_received_data"
        const val ACTION_THICK = "toy.narza.clonetracker.service.Service_thick"
        const val KEY_THICK_VALUE = "thick_value"
        const val KEY_UPDATE_DB = "update_db"
    }

    private val mBinder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        val service: NZService
            get() = this@NZService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(123, NotificationService.getInstance(baseContext).createNotification())
        return Service.START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }

    private var viewModel: DataViewModel? = null
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        viewModel =
            DataViewModelFactory(RoomRepository(applicationContext)).create(DataViewModel::class.java)
        val timer = object : CountDownTimer(180000, 1000) {

            override fun onTick(p0: Long) {
                viewModel?.insertThick(p0)
                emitThick(p0)
            }

            override fun onFinish() {
                emitThick(0)
                start()
                callAPI()
            }
        }

        timer.start()
        callAPI()
    }
    private fun emitThick(value: Long) {
        Intent(ACTION_THICK).apply {
            this.putExtra(KEY_THICK_VALUE, value)
            sendBroadcast(this)
        }
    }

    private fun emitData(dataList: List<CloneData>) {

        Log.e("TEST", "emitData!")
        Intent(ACTION_DATA).apply {
            putExtra(KEY_UPDATE_DB, dataList as java.io.Serializable)
            sendBroadcast(this)
        }
    }

    private fun compareDB(newData: List<CloneData>): CloneData? {

        val ladderData = viewModel?.getLadderData()
        val standardData = viewModel?.getLadderData()

        for (data in newData) {
            ladderData?.value?.let {
                val oldData = it.find { cloneData -> cloneDataComparator(cloneData, data) }
                if (oldData != null) {
                    return oldData
                }
            }

            standardData?.value?.let {
                val oldData = it.find { cloneData -> cloneDataComparator(cloneData, data) }
                if (oldData != null) {
                    return oldData
                }
            }
        }

        return null
    }

    private fun cloneDataComparator(data1: CloneData, data2: CloneData): Boolean {
        return (data1.mode == data2.mode && data1.region == data2.region && data1.ladder == data2.ladder)
                && data1.progress != data2.progress
                || data1.timestamped != data2.timestamped
    }

    private fun callAPI() {

        ApiService.getProgress().enqueue(object : retrofit2.Callback<List<CloneData>> {
            override fun onResponse(
                call: Call<List<CloneData>>, response: Response<List<CloneData>>
            ) {
                response.body().let {
                    scope.launch {
                        if (it != null) {
                            compareDB(it)?.let {
                                notifyUpdate(it)
                            }
                            emitData(it)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CloneData>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, R.string.msg_failed_load_data, Toast.LENGTH_SHORT).show()
            }

        })
    }

    @SuppressLint("ResourceType")
    private fun notifyUpdate(cloneData: CloneData) {

        val ladderId = if (LadderMode.Ladder.index == cloneData.ladder) R.string.mode_ladder else R.string.mode_standard
        val modeId = if (Mode.Softcore.index == cloneData.mode) R.string.mode_sc else R.string.mode_hc
        val textRegion: String = Utils.regionToString(baseContext, cloneData.region)

        val textLadder = applicationContext.resources.getString(ladderId)
        val textMode = applicationContext.resources.getString(modeId)

        val fullMessage = java.lang.StringBuilder(resources.getString(R.string.msg_notify_progress))
            .append(" ")
            .append(textLadder)
            .append("-")
            .append(textRegion)
            .append("-")
            .append(textMode)
            .toString()

        NotificationService.getInstance(baseContext).notify(fullMessage)
    }
}