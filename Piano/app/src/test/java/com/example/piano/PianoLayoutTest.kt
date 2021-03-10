package com.example.piano

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.piano.data.Note
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class PianoLayoutTest {

    @Test
    fun saveFileTest(){
        val note1 = Note("C", 1, 2, 8)
        val note2 = Note("A#", 2, 3, 9)
        val note3 = Note("H", 3, 4, 20)
        val notes = ArrayList<Note>()
        notes.add(note1)
        notes.add(note2)
        notes.add(note3)
        val pianoLayout = PianoLayout()
        pianoLayout.saveToFile(notes, "MyFile")
    }

}