package ru.vezdekod.podcast

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val back: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.back, null)
        supportActionBar?.setHomeAsUpIndicator(back)
        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onFragmentInteraction(navDirections: NavDirections) {
        navController!!.navigate(navDirections)
    }
}


