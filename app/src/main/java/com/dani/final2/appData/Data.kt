package com.dani.final2.appData

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

var textList = mutableStateListOf<String>()
var noteList = mutableStateListOf<Nota>()
val userName = mutableStateOf("")
val userPass = mutableStateOf(false)
var lc = mutableStateOf("")
var lcd = mutableListOf<Double>()
val listState = mutableStateOf<Boolean>(true)