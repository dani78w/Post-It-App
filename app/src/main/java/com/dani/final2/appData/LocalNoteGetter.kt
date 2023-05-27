package com.dani.final2.appData
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material.icons.sharp.HideSource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocalNoteGetter(context: Context):SQLiteOpenHelper(context, "notas", null, 1), CoroutineScope by CoroutineScope(Dispatchers.IO) {
    companion object {
        private const val DATABASE_NAME = "mydatabase.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Define la estructura de tus tablas y crea las tablas aquí
        val createTableQuery = "CREATE TABLE IF NOT EXISTS " +
                "notas (id INTEGER PRIMARY KEY AUTOINCREMENT, text TEXT,x float,y float)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    fun sqlCreateNote(context: Context,localNote: LocalNote){
        localNoteList.add(localNote)
        CoroutineScope(Dispatchers.IO).launch {

            var db = LocalNoteGetter(context)
            var dbw = db.writableDatabase
            try {

                dbw.insert("notas", null, ContentValues().apply {
                    put("text", localNote.text)
                    put("x", localNote.x)
                    put("y", localNote.y)
                })

                Log.d("SQLite","se subio a sqlite")
            }catch (e: Exception){
                Log.d("SQLite", e.toString())
            }

        }

    }

    @SuppressLint("Range")
    fun sqlGetAllNotes(context: Context){
        localNoteList.clear()
        var db = LocalNoteGetter(context)
        var dbR = db.readableDatabase
        var cursor = dbR.rawQuery("SELECT * FROM notas", null)
        if (cursor.moveToNext()) {
            do {
                var text = cursor.getString(cursor.getColumnIndex("text"))
                var x = cursor.getDouble(cursor.getColumnIndex("x"))
                var y = cursor.getDouble(cursor.getColumnIndex("y"))
                var nota = LocalNote(
                    text,
                    x,
                    y
                )
                localNoteList.add(nota)
            } while (cursor.moveToNext())
        }
        Log.d("SQLite", "se obtuvieron las notas "+localNoteList.toString())
    }

    @Composable
    fun showAllNotes(){
        for (i in localNoteList){
            Text(text = i.text,fontSize = 20.sp)
        }
    }


}

class LocalNote(text: String,x: Double,y: Double) {
    var text = ""
    var x : Double = 0.0
    var y : Double = 0.0
}