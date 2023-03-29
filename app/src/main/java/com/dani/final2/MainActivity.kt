package com.dani.final2

import android.Manifest
import android.app.PendingIntent
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.dani.final2.appData.NoteGetter
import com.dani.final2.appData.lc
import com.dani.final2.appData.textList
import com.dani.final2.navigation.AppNavigation


import com.dani.final2.screens.LoginScreen
import com.dani.final2.ui.theme.Final2Theme
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Long

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Final2Theme {
                // A surface container using the 'background' color from the theme
                // Set up the account object with the Auth0 application details

                var a = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                ActivityCompat.requestPermissions(this, a ,1000);
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


                var fusedLocationClient= LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lc.value="Altura   : "+location.altitude.toString()+"\n"+
                                     "Latitud  : "+location.latitude.toString()+"\n"+
                                     "Longitud : "+location.longitude.toString()
                            val nt = NoteGetter()
                            nt.location()
                        }
                    }

                textList.clear()
                hideSystemUI()
                AppNavigation()


                }
            }
        }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }



    }

