package toy.narza.clonetracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.ApiService
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.main.SectionsPagerAdapter
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory
import toy.narza.clonetracker.utils.printPretty
import java.time.Instant

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: DataViewModel
    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        val viewModelFactory = DataViewModelFactory(RoomRepository(this))
        viewModel = ViewModelProvider(this, viewModelFactory)[DataViewModel::class.java]

        fab.setOnClickListener {

            ApiService.getProgress().enqueue(object : retrofit2.Callback<List<CloneData>>{
                override fun onResponse(
                    call: Call<List<CloneData>>,
                    response: Response<List<CloneData>>
                ) {
                    response.body().let {
                        if (it !== null) {
                            printPretty<List<CloneData>>(it)
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            val itemType = object : TypeToken<List<CloneData>>() {}.type
                            viewModel.insertAll(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CloneData>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}