package ru.vezdekod.podcast

import android.annotation.SuppressLint
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
    private var backDirection: NavDirections? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val back: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.back, null)
        supportActionBar?.apply {
            setHomeAsUpIndicator(back)
            title = ""
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        backDirection?.also { navController?.navigate(it) }
        val res = backDirection != null
        backDirection = null
        return res
    }

    override fun onFragmentInteraction(navDirections: NavDirections) {
        navController?.navigate(navDirections)
    }

    override fun setBackDirection(navDirections: NavDirections?) {
        backDirection = navDirections
    }
}


