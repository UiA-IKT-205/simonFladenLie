package com.example.piano

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.piano.data.Note
import com.example.piano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


class PianoLayout : Fragment() {

    private var _binding: FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2")
    private val halfTones = listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#", "C#2", "D#2", "E#2", "F#2", "G#2")
    private var score:MutableList<Note> = mutableListOf<Note>()
    private var noteTracker =  Date().time
    private var saveMelody = false
    private var melodyStart:Long = 0
    private var melodyEnd:Long = 0
    private var totalMelodyTime:Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fullTones.forEach { orgNoteValue ->
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(orgNoteValue)
            var startNote:Long = 0
            var endNote:Long = 0

            fullTonePianoKey.onKeyDown = { note ->
                startNote = (Date().time - noteTracker)
                println("White piano key down $note")
            }

            fullTonePianoKey.onKeyUp = {
                endNote = (Date().time - noteTracker)

                val keyDownDuration = endNote - startNote
                val currentNote = Note(it, startNote, endNote, keyDownDuration)

                if (saveMelody){
                    endNote = (Date().time - melodyStart)
                    score.add(currentNote)
                    println("White piano key up $currentNote")
                }
                else{
                    println("White piano key up $currentNote")
                }
            }

            fragmentTransaction.add(view.fullPianoKeysLayout.id, fullTonePianoKey, "note_$orgNoteValue")
        }

        halfTones.forEach {orgNoteValue ->
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(orgNoteValue)
            var startNote:Long = 0
            var endNote:Long = 0

            halfTonePianoKey.onKeyDown = { note ->
                startNote = (Date().time - noteTracker)
                println("Black piano key down $note")
            }

            halfTonePianoKey.onKeyUp = {
                endNote = (Date().time - noteTracker)
                val keyDownDuration = endNote - startNote
                val currentNote = Note(it, startNote, endNote, keyDownDuration)

                if (saveMelody){
                    endNote = (noteTracker - melodyStart)
                    score.add(currentNote)
                }
                println("Black piano key up $currentNote")
            }

            fragmentTransaction.add(view.halfPianoKeysLayout.id, halfTonePianoKey, "note_$orgNoteValue")
        }

        fragmentTransaction.commit()

        view.startMelodyButton.setOnClickListener{
            startMelody()
        }

        view.endMelodyButton.setOnClickListener{
            endMelody(melodyStart)
        }

        view.saveScoreButton.setOnClickListener{
            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val newFilePath = path.toString() + "/$fileName.music"

            if (score.count() > 0 && fileName.isNotEmpty() && path != null) {
                fileName = "$fileName.music"
                checkIfFileExists(fileName, newFilePath)

                storeMelody(path, fileName)
            }
                else {
                    if (score.count() == 0){
                        Toast.makeText(activity, "Missing keys to store",
                            Toast.LENGTH_LONG).show();
                    }
                    if (fileName.isEmpty()){
                        Toast.makeText(activity, "Name can't be empty",
                                Toast.LENGTH_LONG).show();
                    }
                }   
        }
        return view
    }

    fun startMelody(): Boolean {
        this.melodyStart = (Date().time - noteTracker)
        saveMelody = true

        println(this.melodyStart)

        return saveMelody
    }

    fun endMelody(melodyStart:Long): Boolean {
        // Tracks from melodystart to finish and delivers the total length of the melody when endmelodybutton is pressed
        this.melodyEnd = (Date().time - noteTracker)
        this.totalMelodyTime = (this.melodyEnd - melodyStart)
        saveMelody = false

        println(this.totalMelodyTime)

        return saveMelody
    }

    fun storeMelody(path: File?, fileName:String,){
        FileOutputStream(File(path, fileName), true).bufferedWriter().use { writer ->
            score.forEach {
                writer.write("${it.toString()}\n")
            }
            // Stores the total length of the melody + the name for further usage after tracking the score
            writer.write("Total melody Time: $totalMelodyTime \n Saved as: $fileName")
            score.clear()
        }
    }

    fun checkIfFileExists(directoryFile:String, newFilePath:String): Boolean {
        // Checks for redundant filename
        var doesFileExist = false
        if (directoryFile == newFilePath) {
            doesFileExist = true
            Toast.makeText(context, "Filename already exist", Toast.LENGTH_SHORT).show()
        }
        return doesFileExist
    }

    fun saveToFile(notes: ArrayList<Note>, fileName: String) {
        val path = this.activity?.getExternalFilesDir(null)
        FileOutputStream(File(path, fileName), true).bufferedWriter().use { writer ->
                score.forEach {
                    writer.write("$notes")
                }
            }
        score.clear()
    }
}

