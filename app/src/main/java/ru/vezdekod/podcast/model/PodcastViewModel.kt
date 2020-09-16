package ru.vezdekod.podcast.model

import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import ru.vezdekod.podcast.ui.data.TimecodeData

class PodcastViewModel : ViewModel() {

    var mediaPlayer: MediaPlayer? = null
    var fileUri: Uri? = null

    val timecodes = mutableListOf<TimecodeData>()
}