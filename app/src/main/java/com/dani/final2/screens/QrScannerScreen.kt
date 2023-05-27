package com.dani.final2.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.io.IOException

@Composable
fun qrScannerScreen(navController: NavHostController) {
    var context = LocalContext.current
    AndroidView(
        factory = { context ->
            View(context).apply {
                // Se solicita el permiso de la cámara
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(android.Manifest.permission.CAMERA),
                    111
                )
            }

        },
        update = { view ->
            // No se necesita implementación en este caso
        }
    )

}