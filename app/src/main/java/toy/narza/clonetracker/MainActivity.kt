package toy.narza.clonetracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.ApiService
import toy.narza.clonetracker.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {
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

        fab.setOnClickListener { view ->

            ApiService.getProgress().enqueue(object : retrofit2.Callback<List<CloneData>>{
                override fun onResponse(
                    call: Call<List<CloneData>>,
                    response: Response<List<CloneData>>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<List<CloneData>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}