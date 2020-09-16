package ru.vezdekod.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onFragmentInteraction(navDirections: NavDirections) {
        navController!!.navigate(navDirections)
    }
}