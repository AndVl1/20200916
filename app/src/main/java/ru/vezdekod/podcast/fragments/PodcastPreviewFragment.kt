package ru.vezdekod.podcast.fragments

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import ru.vezdekod.podcast.OnFragmentInteractionListener
import ru.vezdekod.podcast.databinding.FragmentPodcastPreviewBinding
import java.util.*

class PodcastPreviewFragment : Fragment() {

    private lateinit var viewBinding: FragmentPodcastPreviewBinding

    private var onFragmentInteractionListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onFragmentInteractionListener = context as? OnFragmentInteractionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPodcastPreviewBinding.inflate(inflater, container, false).also {
        viewBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.nextButton.setOnClickListener {
            val navDirections: NavDirections =
                PodcastPreviewFragmentDirections.actionNavPodcastPreviewToNavEnd()
            onFragmentInteractionListener?.onFragmentInteraction(navDirections)
        }
        Objects.requireNonNull((requireActivity() as AppCompatActivity).supportActionBar)
            ?.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull((requireActivity() as AppCompatActivity).supportActionBar)
            ?.setTitle("Редактирование")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.home == item.itemId) {
            /**Навигиция назад */
            Toast.makeText(context, "Back", Toast.LENGTH_LONG).show()
        }
        return true
    }
}