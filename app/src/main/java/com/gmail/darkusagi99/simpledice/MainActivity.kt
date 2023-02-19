package com.gmail.darkusagi99.simpledice

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialisation de la vue
        setContentView(R.layout.activity_main)

        DiceInfo.addDice(DiceInfo.createNewDice())
        DiceInfo.addDice(DiceInfo.createNewDice())

        // Mise en place des boutons
        findViewById<FloatingActionButton>(R.id.addActionButton).setOnClickListener { view ->

            val taskEditText =  EditText(view.context)
            //val diceEditSlider = (View.context)
            val dialogClickListener =
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            Toast.makeText(this, "Création - " + taskEditText.text.toString(), Toast.LENGTH_SHORT).show()
                            DiceInfo.addDice(DiceInfo.createNewDice())
                        }
                    }
                }
            val ab: AlertDialog.Builder = AlertDialog.Builder(view.context)
            ab.setMessage("Ajouter dé ?")
                .setView(taskEditText)
                .setPositiveButton("OK", dialogClickListener).show()
        }

        // Initialisation de la recylerView
        setupRecyclerView(findViewById(R.id.DiceRw))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(DiceInfo.ITEMS)
    }

    class SimpleItemRecyclerViewAdapter(private val values: List<DiceInfo.DiceItem>) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.dice_entry, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.diceButton.text = item.lastRoll.toString()

            with(holder.itemView) {
                tag = item
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val diceButton: Button = view.findViewById(R.id.diceDisplay)
        }
    }
}