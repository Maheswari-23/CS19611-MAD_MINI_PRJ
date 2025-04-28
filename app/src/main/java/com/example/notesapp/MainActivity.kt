package com.example.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etNote: EditText
    private lateinit var btnAddNote: Button
    private lateinit var lvNotes: ListView
    private val notesList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        etNote = findViewById(R.id.etNote)
        btnAddNote = findViewById(R.id.btnAddNote)
        lvNotes = findViewById(R.id.lvNotes)

        // Create a custom adapter for ListView with edit and delete buttons
        val adapter = object : BaseAdapter() {
            override fun getCount(): Int = notesList.size

            override fun getItem(position: Int): Any = notesList[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.list_item_note, parent, false)

                val noteText = notesList[position]
                val tvNote = view.findViewById<TextView>(R.id.tvNote)
                val btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
                val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)

                tvNote.text = noteText

                // Edit button click listener
                btnEdit.setOnClickListener {
                    etNote.setText(noteText)
                    notesList.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, "Note ready for editing", Toast.LENGTH_SHORT).show()
                }

                // Delete button click listener
                btnDelete.setOnClickListener {
                    notesList.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
                }

                return view
            }
        }

        lvNotes.adapter = adapter

        // Button click listener to add a note
        btnAddNote.setOnClickListener {
            val noteText = etNote.text.toString().trim()
            if (noteText.isNotEmpty()) {
                // Add note to the list and notify the adapter
                notesList.add(noteText)
                adapter.notifyDataSetChanged()  // Update the ListView
                etNote.text.clear() // Clear the EditText after adding
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}