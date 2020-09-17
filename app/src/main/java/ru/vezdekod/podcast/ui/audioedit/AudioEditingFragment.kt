package ru.vezdekod.podcast.ui.audioedit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vezdekod.podcast.OnFragmentInteractionListener
import ru.vezdekod.podcast.R
import ru.vezdekod.podcast.databinding.FragmentAudioEditingBinding
import ru.vezdekod.podcast.fragments.MainPodcastDataFragmentDirections
import ru.vezdekod.podcast.model.PodcastViewModel
import ru.vezdekod.podcast.ui.data.TimecodeDataSource

class AudioEditingFragment : Fragment() {
    private var timecodesRecycler: RecyclerView? = null
    private lateinit var viewBinding: FragmentAudioEditingBinding

    private var onFragmentInteractionListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentInteractionListener = context as? OnFragmentInteractionListener
        onFragmentInteractionListener?.setBackDirection(AudioEditingFragmentDirections.actionNavAudioEditingToNavMainPodcastData())
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.screen_title_audio_edit)
            show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAudioEditingBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    private val timecodeViewModel: AudioEditingViewModel by viewModels()
    private val globalViewModel: PodcastViewModel by viewModels({ requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.addTimecodeButton.setOnClickListener {
            timecodeViewModel.addTimecode()
        }

        viewBinding.fragmentAudioEditingButtonFinishEditing.setOnClickListener {
            val navDirections: NavDirections =
                AudioEditingFragmentDirections.actionNavAudioEditingToNavMainPodcastData()
            onFragmentInteractionListener?.onFragmentInteraction(navDirections)
        }

        initList(view)

        timecodeViewModel.timecodesLiveData.observe(viewLifecycleOwner, {
            updateList(it)
            globalViewModel.timecodes.apply {
                clear()
                addAll(it.timecodes)
            }
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