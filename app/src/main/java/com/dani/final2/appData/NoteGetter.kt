package com.dani.final2.appData

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material.icons.sharp.HideImage
import androidx.compose.material.icons.sharp.HideSource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.lang.Thread.sleep

class NoteGetter() : ViewModel() {

    fun location() {
        val db = Firebase.firestore
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("Location").add("location" to lc.value)
        }
    }

    fun getNotes() {
        sleep(25)
        val db = Firebase.firestore
        viewModelScope.launch(Dispatchers.IO) {

            for (i in db.collection("notes").get().await().documents) {
                if ((textList.contains(i.get("note").toString()))) {
                    continue
                } else {
                    textList.add(i.get("note").toString())
                    noteList.add(Nota(i.id, i.get("note").toString(), i.get("ubi").toString()))
                }


            }

        }
        sleep(25)
    }

    fun waitToSincronize() {
        viewModelScope.launch(Dispatchers.IO) {
            sleep(70)
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

    fun purgeDuplicates() {
        var auxList = mutableListOf<Nota>()
        var flag = false
        for (i in noteList) {

            if (auxList.isEmpty()) {
                auxList.add(i)
            } else {

                for (j in auxList) {
                    if (i.text == j.text) {
                        flag = true
                    }
                }
            }

            if (flag == false) {
                auxList.add(i)
            }

        }
        noteList.clear()
        noteList.addAll(auxList)
    }

    @Composable
    fun showNotes(scope: CoroutineScope, snackbarHostState: SnackbarHostState) {

        if (!(noteList.isEmpty())) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .wrapContentHeight()
                    .animateContentSize(
                        animationSpec = tween(500)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.ShareLocation,
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "Notas compartidas",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = "compartido por: " + userName.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )

            }

        }
        var j = 0

        for (i in noteList.distinct()) {
            waitToSincronize()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.shapes.medium
                    )
                    .animateContentSize(
                        animationSpec = tween(500)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!i.equals("N")) {
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .fillMaxWidth()
                    ) {

                        Text(
                            text = "NOTA",
                            modifier = Modifier
                                .padding(start = 9.dp)
                                .align(Alignment.CenterVertically)
                                .weight(0.4f),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.inverseSurface
                        )

                        Icon(imageVector = Icons.Sharp.HideSource,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    noteList.remove(i)
                                }
                                .align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                        j++
                        Icon(imageVector = Icons.Default.AddLocation,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    if (i.ubi.equals("")) {
                                        i.ubi = lc.value
                                        var aux = hashMapOf(
                                            "id" to i.id,
                                            "note" to i.text,
                                            "ubi" to i.ubi
                                        )

                                        val db = Firebase.firestore
                                        db
                                            .collection("notes")
                                            .document(i.id)
                                            .update(aux as Map<String, Any>)
                                    } else {
                                        var aux = hashMapOf(
                                            "id" to i.id,
                                            "note" to i.text,
                                            "ubi" to "",

                                            )
                                        i.ubi = lc.value
                                        val db = Firebase.firestore
                                        db
                                            .collection("notes")
                                            .document(i.id)
                                            .update(aux as Map<String, Any>)

                                    }



                                }
                                .align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )

                        Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    noteList.remove(i)

                                    val db = Firebase.firestore
                                    db
                                        .collection("notes")
                                        .document(i.id)
                                        .delete()
                                }
                                .align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.inverseSurface
                        )
                    }
                }
                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)

                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Column() {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = i.text,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        if (!(i.ubi.equals(""))) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(129.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.shapes.medium
                                    )
                                    .animateContentSize(
                                        animationSpec = tween(500)
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {

                                Icon(
                                    imageVector = Icons.Outlined.ViewInAr,
                                    contentDescription = "da",
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .weight(0.2f)
                                        .size(35.dp)
                                )
                                Text(
                                    text = i.ubi.toString(),
                                    modifier = Modifier
                                        .weight(0.6f)
                                        .padding(10.dp)
                                )
                            }
                        }
                    }

                }
            }

        }
        Spacer(modifier = Modifier.height(240.dp))

    }
}