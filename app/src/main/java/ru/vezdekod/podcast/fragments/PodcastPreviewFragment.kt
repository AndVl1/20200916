package ru.vezdekod.podcast.fragments

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
import ru.vezdekod.podcast.databinding.FragmentPodcastPreviewBinding
import ru.vezdekod.podcast.databinding.PreviewTimecodeItemBinding
import ru.vezdekod.podcast.model.PodcastViewModel
import ru.vezdekod.podcast.ui.audioedit.AudioEditingFragmentDirections
import java.util.*

class PodcastPreviewFragment : Fragment() {

    private lateinit var viewBinding: FragmentPodcastPreviewBinding
    private val viewModel: PodcastViewModel by viewModels({ requireActivity() })

    private var onFragmentInteractionListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentInteractionListener = context as? OnFragmentInteractionListener
        onFragmentInteractionListener?.setBackDirection(PodcastPreviewFragmentDirections.actionNavPodcastPreviewToNavAudioEditing())
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.screen_title_new_podcast)
            show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPodcastPreviewBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.podcastTitleTv.text = viewModel.podcastName
        viewBinding.podcastDescriptionTv.text = viewModel.podcastDescription

        viewBinding.nextButton.setOnClickListener {
            val navDirections: NavDirections =
                PodcastPreviewFragmentDirections.actionNavPodcastPreviewToNavEnd()
            onFragmentInteractionListener?.onFragmentInteraction(navDirections)
        }

        val adapter = object : RecyclerView.Adapter<TimecodeViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimecodeViewHolder {
                return TimecodeViewHolder(
                    PreviewTimecodeItemBinding.inflate(
                        LayoutInflater.from(requireContext()), parent, false
                    )
                )
            }

            override fun onBindViewHolder(holder: TimecodeViewHolder, position: Int) {
                holder.viewBinding.timecodeTimeTv.text = viewModel.timecodes[position].time
                holder.viewBinding.timecodeDescriptionTv.text = viewModel.timecodes[position].name
            }

            override fun getItemCount(): Int {
                return viewModel.timecodes.size
            }

        }

        viewBinding.timecodeListRecycler.adapter = adapter
        viewBinding.timecodeListRecycler.layoutManager = LinearLayoutManager(requireContext())
    }

    private inner class TimecodeViewHolder(val viewBinding: PreviewTimecodeItemBinding) : RecyclerView.ViewHolder(
        viewBinding.root
    )
}