package ru.vezdekod.podcast.ui.audioedit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vezdekod.podcast.ui.data.TimecodeData
import ru.vezdekod.podcast.ui.data.TimecodeDataSource

class AudioEditingViewModel: ViewModel() {

    val timecodesLiveData = MutableLiveData<TimecodeDataSource>()
    private val timecodes = TimecodeDataSource()

    init {
        timecodesLiveData.value = timecodes
    }

    fun addTimecode() {
        timecodes.add(TimecodeData("", ""))
        timecodesLiveData.value = timecodes
    }
}