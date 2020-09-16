package ru.vezdekod.podcast.ui.data

import java.io.Serializable

class TimecodeDataSource: Serializable {
    val timecodes = ArrayList<TimecodeData>()

    fun add(data: TimecodeData) {
        timecodes.add(data)
    }

    operator fun get(pos: Int): TimecodeData = timecodes[pos]
}