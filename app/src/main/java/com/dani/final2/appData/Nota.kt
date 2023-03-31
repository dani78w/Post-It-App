package com.dani.final2.appData

import android.icu.text.DateFormat.HourCycle
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import com.google.type.Date
import com.google.type.DateTime
import java.sql.Time

class Nota {
    var id :String = ""
    var text :String = ""
    var ubi :String = "No existe"


    constructor(text: String, ubi: String) {
        this.text = text
        this.ubi = ubi
    }

    constructor(id: String, text: String, ubi: String) {
        this.id = id
        this.text = text
        this.ubi = ubi
    }

    fun setUbication(ubi:String){
        this.ubi = ubi
    }
}
