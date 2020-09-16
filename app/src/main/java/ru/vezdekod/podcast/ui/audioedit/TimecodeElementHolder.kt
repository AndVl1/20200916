package ru.vezdekod.podcast.ui.audioedit

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import ru.vezdekod.podcast.R

class TimecodeElementHolder(itemView: View, adapter: TimecodeAdapter) : RecyclerView.ViewHolder(itemView) {
    private val nameText: EditText
    private val timeText: EditText

    init {
        val removeButton = itemView.findViewById<ImageButton>(R.id.delete_timecode_button)
        removeButton.setOnClickListener {
            adapter.removeItem(adapterPosition)
        }
        nameText = itemView.findViewById(R.id.timecode_name_input)
        nameText.doOnTextChanged { text, start, count, after ->
            text?.toString()?.let { adapter.updateName(adapterPosition, it) }
        }
        timeText = itemView.findViewById(R.id.timecode_time_input)
        timeText.doOnTextChanged { text, start, count, after ->
            text?.toString()?.let { adapter.updateTime(adapterPosition, it) }
        }
    }

    fun bind(text: String, time: String) {
        nameText.setText(text)
        nameText.requestFocus()
        timeText.setText(time)
    }
}