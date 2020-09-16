package ru.vezdekod.podcast.ui.audioedit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import ru.vezdekod.podcast.R
import ru.vezdekod.podcast.ui.data.TimecodeDataSource

class TimecodeAdapter (val source: TimecodeDataSource) : RecyclerView.Adapter<TimecodeElementHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimecodeElementHolder
     = TimecodeElementHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.timecode_element, parent, false),
        this
    )

    override fun onBindViewHolder(holder: TimecodeElementHolder, position: Int) {
        holder.bind(source.timecodes[position].name, source.timecodes[position].time)
    }

    override fun getItemCount(): Int = source.timecodes.size

    fun removeItem(position: Int){
        source.timecodes.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateName(position: Int, text: String) {
        source[position].name = text
    }

    fun updateTime(position: Int, time: String) {
        source[position].time = time
    }
}