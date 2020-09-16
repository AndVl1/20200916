package ru.vezdekod.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.vezdekod.podcast.ui.audioedit.AudioEditingFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frame = supportFragmentManager.findFragmentById(R.id.main_frame)
        if (frame != null) return
        supportFragmentManager.beginTransaction().add(R.id.main_frame, AudioEditingFragment())
            .commit()
    }
}