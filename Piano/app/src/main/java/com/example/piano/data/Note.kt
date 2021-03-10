package com.example.piano.data

data class Note(val value: String, val start: Long, val end: Long, val keyDownDuration: Long){

    override fun toString(): String {
        return "$value,$start,$end"
    }
}
