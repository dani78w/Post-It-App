package com.dani.final2.appData

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

var textList = mutableStateListOf<String>()
val userName = mutableStateOf("")
val userPass = mutableStateOf(false)