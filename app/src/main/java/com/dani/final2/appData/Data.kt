package com.dani.final2.appData

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

var textList = mutableStateListOf<String>()
var noteList = mutableStateListOf<Nota>()
val userName = mutableStateOf("Anonimo")
val userPass = mutableStateOf(false)
val userEmail = mutableStateOf("Anonimo@anon.com")
var lc = mutableStateOf("")
var lcd = mutableListOf<Double>()
val listState = mutableStateOf<Boolean>(true)

var session = "test"
var sessionPasword= "test"
var localNoteList = mutableStateListOf<LocalNote>()
var noteSelected = mutableStateOf(0)