package ru.vezdekod.podcast.model

import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import ru.vezdekod.podcast.ui.data.TimecodeData

class PodcastViewModel : ViewModel() {

    var podcastName: String? = null
    var podcastDescription: String? = null
    var podcastImage: Bitmap? = null

    var mediaPlayer: MediaPlayer? = null
    var fileUri: Uri? = null

    val timecodes = mutableListOf<TimecodeData>()

    var adultContent = false
    var excludeFromExport = false
    var trailer = false

    fun clear() {
        podcastName = null
        podcastDescription = null
        podcastImage = null
        mediaPlayer?.release()
        mediaPlayer = null
        fileUri = null
        timecodes.clear()
        adultContent = false
        excludeFromExport = false
        trailer = false
    }
}