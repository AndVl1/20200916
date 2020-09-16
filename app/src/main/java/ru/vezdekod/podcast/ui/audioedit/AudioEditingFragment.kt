package ru.vezdekod.podcast.ui.audioedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vezdekod.podcast.R
import ru.vezdekod.podcast.ui.data.TimecodeDataSource

class AudioEditingFragment : Fragment() {
    private var timecodesRecycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_audio_editing, container, false)

    private val timecodeViewModel: AudioEditingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.add_timecode_button)
            .setOnClickListener {
                timecodeViewModel.addTimecode()
            }

        initList(view)

        timecodeViewModel.timecodesLiveData.observe(viewLifecycleOwner, Observer{
            updateList(it)
        })
    }

    private fun initList(root: View) {
        timecodesRecycler = root.findViewById(R.id.timecode_list_recycler)
        updateList(TimecodeDataSource())
    }

    private fun updateList(dataSource: TimecodeDataSource) {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val adapter = TimecodeAdapter(dataSource)
        timecodesRecycler?.layoutManager = linearLayoutManager
        timecodesRecycler?.adapter = adapter
    }
}