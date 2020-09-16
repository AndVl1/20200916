package ru.vezdekod.podcast.ui.audioedit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vezdekod.podcast.R
import ru.vezdekod.podcast.ui.data.TimecodeDataSource

class TimecodeAdapter (val source: TimecodeDataSource) : RecyclerView.Adapter<TimecodeElementHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimecodeElementHolder
     = TimecodeElementHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.timecode_element, parent, false)
    )

    override fun onBindViewHolder(holder: TimecodeElementHolder, position: Int) {

    }

    override fun getItemCount(): Int = source.timecodes.size

}