package toy.narza.clonetracker

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.service.NZService
import toy.narza.clonetracker.service.NzReceiver
import toy.narza.clonetracker.ui.main.SectionsPagerAdapter
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: DataViewModel
    private val receiver: NzReceiver = NzReceiver()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        val viewModelFactory = DataViewModelFactory(RoomRepository(applicationContext))
        viewModel = ViewModelProvider(this, viewModelFactory)[DataViewModel::class.java]

        val image = if(isServiceRunning()) getDrawable(R.drawable.diablo_indicator) else getDrawable(R.drawable.baseline_refresh_white_36)
        fab.setImageDrawable(image)
        fab.setOnClickListener {

            if (isServiceRunning()) {
                stopService(Intent(baseContext, NZService::class.java))
                fab.setImageDrawable(getDrawable(R.drawable.diablo_indicator))
            } else {
                getDrawable(R.drawable.baseline_refresh_white_36)
                startService(Intent(baseContext, NZService::class.java))
            }
        }

        val timerView = findViewById<ProgressBar>(R.id.timer_bar)

        val thickLiveData = viewModel.getThick()
        thickLiveData.observe(this) {
            if (it != null) {
                timerView.progress = timerView.max - it.toInt()
            }
        }

        receiver.setViewModel(viewModel)

        IntentFilter()
            .also {
                it.addAction(NZService.ACTION_DATA)
                it.addAction(NZService.ACTION_THICK)
                registerReceiver(receiver, it)
            }
    }


    @Suppress("DEPRECATION")
    private fun isServiceRunning(): Boolean {
        val manager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (runningService in manager.getRunningServices(Int.MAX_VALUE)) {
            if (NZService::class.java.name == runningService.service.className) {
                return true
            }
        }
        return false;
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

}