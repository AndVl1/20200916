package ru.vezdekod.podcast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.vezdekod.podcast.databinding.FragmentPodcastPreviewBinding

class PodcastPreviewFragment : Fragment() {

    private lateinit var viewBinding: FragmentPodcastPreviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPodcastPreviewBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}