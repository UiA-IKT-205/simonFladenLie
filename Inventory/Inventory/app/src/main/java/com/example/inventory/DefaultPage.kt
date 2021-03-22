package com.example.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.databinding.ActivityMainBinding
import com.example.inventory.databinding.FragmentDefaultPageLayoutBinding
import kotlinx.android.synthetic.main.fragment_default_page_layout.*


/**
 * A simple fragment subclass as the main(default) destination in the navigation.
 * TODO: Change name of the activities to something more suitable
 * TODO: Add some commenting
 */
class DefaultPage : Fragment() {

    private var _binding: FragmentDefaultPageLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDefaultPageLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    // Generating a dummy list for testing purposes
    private fun generateDummyList(size: Int): List<RecyclerViewItem> {
        val list = ArrayList<RecyclerViewItem>()
        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.drawable.ic_baseline_6_ft
                1 -> R.drawable.ic_baseline_accessible
                else -> R.drawable.ic_baseline_5g
            }
            val item = RecyclerViewItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testList = generateDummyList(20)

        recycler_view.adapter = RecyclerItemAdapter(testList)
        // Change linearLayoutManager to make a grid system
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)

        binding.addNewList.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}