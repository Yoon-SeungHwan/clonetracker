package toy.narza.clonetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.ApiService
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.main.SectionsPagerAdapter
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory
import java.io.IOException
import java.io.InputStream

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
                            viewModel.insertAll(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CloneData>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, R.string.msg_failed_load_data, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    fun loadJSONFromAsset(): String {
        try {
            val `is`: InputStream = this.assets.open("Sample.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()

            return String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return "[]"
        }
    }

}