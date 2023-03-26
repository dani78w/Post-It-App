package com.dani.final2.appData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Thread.sleep

class NoteGetter(): ViewModel() {


    fun getNotes() {
        val db = Firebase.firestore
        viewModelScope.launch(Dispatchers.IO) {
            sleep(500)
            for (i in db.collection("notes").get().await().documents) {
                if (textList.contains(i.get("note").toString())) {
                    continue
                }else{

                    textList.add(i.get("note").toString())
                }
            }

        }
    }
    fun deleteNotes() {
        val db = Firebase.firestore
        textList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("notes").get().await().documents.forEach {
                it.reference.delete()
            }
        }
    }
    fun deleteNote(note: String) {
        val db = Firebase.firestore
        textList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("notes").get().await().documents.forEach {
                if (it.get("note").toString() == note) {
                    it.reference.delete()
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("notes").get().await().documents.forEach {
                it.reference.delete()
            }
        }
    }
}