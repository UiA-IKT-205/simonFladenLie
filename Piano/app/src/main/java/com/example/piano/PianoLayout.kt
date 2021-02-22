package com.example.piano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piano.databinding.FragmentPianoLayoutBinding
import kotlinx.android.synthetic.main.fragment_piano_layout.view.*

class PianoLayout : Fragment() {

    private var _binding: FragmentPianoLayoutBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2")
    private val halfTones = listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#", "C#2", "D#2", "E#2", "F#2", "G#2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoLayoutBinding.inflate(layoutInflater)
        val view = binding.root

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fullTones.forEach {
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            fullTonePianoKey.onKeyDown = {
                println("White piano key down $it")
            }

            fullTonePianoKey.onKeyUp = {
                println("White piano key up $it")
            }

            fragmentTransaction.add(view.fullPianoKeysLayout.id, fullTonePianoKey, "note_$it")
        }

        halfTones.forEach {
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)

            halfTonePianoKey.onKeyDown = {
                println("Black piano key down $it")
            }

            halfTonePianoKey.onKeyUp = {
                println("Black piano key up $it")
            }

            fragmentTransaction.add(view.halfPianoKeysLayout.id, halfTonePianoKey, "note_$it")
        }

        return inflater.inflate(R.layout.fragment_piano_layout, container, false)
    }
}
