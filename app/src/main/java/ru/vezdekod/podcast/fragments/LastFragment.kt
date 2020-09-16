package ru.vezdekod.podcast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.vezdekod.podcast.databinding.FragmentLastBinding

class LastFragment : Fragment() {

    private lateinit var viewBinding: FragmentLastBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLastBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.closeMessageButton.setOnClickListener {

        }

        viewBinding.sharePodcastButton.setOnClickListener {

        }
    }

}