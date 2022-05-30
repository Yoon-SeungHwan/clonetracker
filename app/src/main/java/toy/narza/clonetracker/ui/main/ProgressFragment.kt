package toy.narza.clonetracker.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import toy.narza.clonetracker.R
import toy.narza.clonetracker.db.CloneData
import toy.narza.clonetracker.network.constants.LadderMode
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
        val asia: ProgressCard = root.findViewById(R.id.progress_asia)
        val america: ProgressCard = root.findViewById(R.id.progress_america)
        val europe: ProgressCard = root.findViewById(R.id.progress_europe)
        val observer = Observer<List<CloneData>> {
            it.find { data -> data.region == Region.Americas.index }?.let { data ->
                america.setData(data)
            }
            it.find { data -> data.region == Region.Europe.index }?.let { data ->
                europe.setData(data)
            }
            it.find { data -> data.region == Region.Asia.index }?.let { data ->
                asia.setData(data)
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