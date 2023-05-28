package com.dani.final2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.ContentValues
import android.content.ContextWrapper
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationListenerCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dani.final2.appData.*
import com.dani.final2.navigation.AppNavigation


import com.dani.final2.screens.LoginScreen
import com.dani.final2.ui.theme.Final2Theme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Long
import kotlin.math.log

class MainActivity : ComponentActivity(), CoroutineScope by CoroutineScope(Dispatchers.IO) {



    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Final2Theme {

                //ubicacion
                hideSystemUI()
                cargarUbicacion()
                textList.clear()
                AppNavigation()


            }
        }
    }
    fun sqlite(){
        var db = LocalNoteGetter(this)
        var dbw = db.writableDatabase
        db.sqlGetAllNotes(this)
        db.sqlCreateNote(
            this,
            LocalNote(
                "Nota 1",
                1.0,1.0))
        db.sqlCreateNote(
            this,
            LocalNote(
                "Nota 1jnssjcpsdaipasdfaasdadasdadadsdf asdasdasdasdasd apsidf",
                1.0,1.0))
    }
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    fun cargarUbicacion(){

        var a = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, a, 1000);
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        CoroutineScope(Dispatchers.IO).launch {

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            lcd.add(location.altitude)
                            lcd.add(location.latitude)
                            lcd.add(location.longitude)

                            lc.value = "Altura   : " + location.altitude.toString() + "\n" +
                                    "Latitud  : " + location.latitude.toString() + "\n" +
                                    "Longitud : " + location.longitude.toString()
                            val nt = NoteGetter()
                            nt.location()
                        } else {
                            lc.value = "No se pudo obtener la ubicacion"
                        }
                    }




        }
    }

    @Composable
    fun qrcode(){
        var contexto = LocalContext.current

        try {
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_AZTEC)
                .build()


            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            ActivityCompat.startActivityForResult(contexto as Activity, intent, 1, null)

            val uri: Uri? = intent.data
            val image: InputImage = uri?.let { InputImage.fromFilePath(contexto, it) }!!
        } catch (e : java.lang.Exception) {
            e.printStackTrace()
        }
    }


}

