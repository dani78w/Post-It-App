package com.dani.final2

import androidx.navigation.NavController
import com.dani.final2.appData.userPass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun createAcount(navController: NavController,email: String, password: String):Boolean {
    var output = false
    val db = Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    val user = hashMapOf(
        "email" to email,
        "password" to password
    )
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful != null) {
                print("Cuenta creada")
                output=!output
                navController.navigate("CreateAcountScreen")
            } else {
                print("Error")
            }
        }
    db.collection("users").document().set(user)
    userPass.value=true
    return output
}