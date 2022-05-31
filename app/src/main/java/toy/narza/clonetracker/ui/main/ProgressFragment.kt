package toy.narza.clonetracker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.constants.LadderMode
import toy.narza.clonetracker.network.constants.Mode
import toy.narza.clonetracker.network.constants.Region
import toy.narza.clonetracker.repositories.RoomRepository
import toy.narza.clonetracker.ui.custom.ProgressCard
import toy.narza.clonetracker.ui.viewmodel.DataViewModel
import toy.narza.clonetracker.ui.viewmodel.DataViewModelFactory

class ProgressFragment : Fragment() {
    private lateinit var viewModel: DataViewModel
    private lateinit var ladderMode: LadderMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = DataViewModelFactory(RoomRepository(requireContext()))
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(DataViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ladderMode =
            (arguments?.get(ProgressFragment.LADDER_MODE) ?: LadderMode.Standard) as LadderMode

        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val observer = Observer<List<CloneData>> {

            val hcData = it.filter { data -> data.mode == Mode.Hardcore.index }
            val scData = it.filter { data -> data.mode == Mode.Softcore.index }


            for (data in scData) {
                when (data.region) {
                    Region.Americas.index -> root.findViewById<ProgressCard>(R.id.progress_america).setData(data)
                    Region.Europe.index -> root.findViewById<ProgressCard>(R.id.progress_europe).setData(data)
                    Region.Asia.index -> root.findViewById<ProgressCard>(R.id.progress_asia).setData(data)
                }
            }
            for (data in hcData) {
                when (data.region) {
                    Region.Americas.index -> root.findViewById<ProgressCard>(R.id.progress_america_hc).setData(data)
                    Region.Europe.index -> root.findViewById<ProgressCard>(R.id.progress_europe_hc).setData(data)
                    Region.Asia.index -> root.findViewById<ProgressCard>(R.id.progress_asia_hc).setData(data)
                }
            }
        }

        when (ladderMode) {
            LadderMode.Ladder -> {
                viewModel.ladderDataList.observe(viewLifecycleOwner, observer)
                viewModel.getLadderData()
            }
            LadderMode.Standard -> {
                viewModel.standardDataList.observe(viewLifecycleOwner, observer)
                viewModel.getStandardData()
            }
        }

        return root
    }


    companion object {
        private const val LADDER_MODE = "ladder_mode"

        @JvmStatic
        fun newInstance(ladderMode: LadderMode): ProgressFragment {
            return ProgressFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(LADDER_MODE, ladderMode)
                }
            }
        }
    }
}