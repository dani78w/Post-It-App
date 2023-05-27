package com.dani.final2.appData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionGetter: ViewModel() {
    fun set(session: String, sessionPasword: String){

        viewModelScope.launch(Dispatchers.IO) {
            val sesion = hashMapOf(
                "id" to session,
                "password" to sessionPasword,

            )
            val db = Firebase.firestore
            //db.collection("notes").document().set(note)
            var route = session
            db.collection("sesiones").document().set(sesion)

            com.dani.final2.appData.session = session
            com.dani.final2.appData.sessionPasword = sessionPasword
        }

    }
}