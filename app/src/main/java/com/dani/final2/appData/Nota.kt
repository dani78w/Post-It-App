package com.dani.final2.appData

import android.icu.text.DateFormat.HourCycle
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import com.google.type.Date
import com.google.type.DateTime
import java.sql.Time

class Nota {
    var text :String = ""
    var ubi :String = "No existe"
    lateinit var time :Date.Builder
    constructor(text: String) {
        this.text = text
    }

    constructor(text: String, ubi: String) {
        this.text = text
        this.ubi = ubi
    }

    fun setUbication(ubi:String){
        this.ubi = ubi
    }
}
