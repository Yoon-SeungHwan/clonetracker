package toy.narza.clonetracker.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.ApiService
import toy.narza.clonetracker.network.`interface`.CloneDiaData
import toy.narza.clonetracker.network.constants.LadderMode
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory

class ProgressFragment : Fragment() {
    private lateinit var viewModel: DataViewModel
    private lateinit var ladderMode: LadderMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = DataViewModelFactory(RoomRepository(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(DataViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ladderMode = (savedInstanceState?.get(ProgressFragment.LADDER_MODE) ?: LadderMode.Standard) as LadderMode
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)

        val observer = Observer<List<CloneData>>{
            textView.text = "Size -> ${it.size}"
        }

        when (ladderMode) {
            LadderMode.Ladder -> viewModel.ladderDataList.observe(viewLifecycleOwner, observer)
            LadderMode.Standard -> viewModel.standardDataList.observe(viewLifecycleOwner, observer)
        }


        return root
    }

    companion object {
        private const val LADDER_MODE = "ladder_mode"
        @JvmStatic
        fun newInstance(ladderMode: LadderMode) = ProgressFragment().apply {
            arguments = Bundle().apply {
                Log.e("Test", "LadderMode -> ${ladderMode.name}")
                putSerializable(LADDER_MODE, ladderMode)
            }
        }
    }
}