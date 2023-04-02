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
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialisation de la vue
        setContentView(R.layout.activity_main)

        DiceInfo.addDice(DiceInfo.createNewDice(6))
        DiceInfo.addDice(DiceInfo.createNewDice(6))

        // Initialisation de la recylerView
        setupRecyclerView(findViewById(R.id.DiceRw))

        // Mise en place des boutons
        findViewById<FloatingActionButton>(R.id.addActionButton).setOnClickListener { view ->

            val taskEditText =  EditText(view.context)
            //val diceEditSlider = (View.context)
            val dialogClickListener =
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            Toast.makeText(this, "Création - " + taskEditText.text.toString(), Toast.LENGTH_SHORT).show()
                            val maxDiceValue = taskEditText.text.toString().toIntOrNull() ?: 6
                            val newDicePosition = DiceInfo.addDice(DiceInfo.createNewDice(maxDiceValue))

                            // Find adapter and notify idem added
                            val recyclerView : RecyclerView = findViewById(R.id.DiceRw)
                            recyclerView.adapter?.notifyItemInserted(newDicePosition)
                        }
                    }
                }

            if (DiceInfo.SETTING_MODE == 1) {
                // Config mode -> Add button
                val ab: AlertDialog.Builder = AlertDialog.Builder(view.context)
                ab.setMessage("Ajouter dé ?")
                    .setView(taskEditText)
                    .setPositiveButton("OK", dialogClickListener).show()
            } else {
                // Normal mode -> Roll all dices
                DiceInfo.rollAllDices()

                // Find adapter and notify list refresh
                val recyclerView : RecyclerView = findViewById(R.id.DiceRw)
                recyclerView.adapter?.notifyItemRangeChanged(0, DiceInfo.ITEMS.size)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val sw = menu!!.findItem(R.id.app_setting_switch).actionView!!
            .findViewById(R.id.settingSwitch) as Switch

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                DiceInfo.SETTING_MODE = 1
            } else {
                DiceInfo.SETTING_MODE = 0
            }
        }

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

            // Show dice value
            holder.diceButton.text = item.lastRoll.toString()

            // Add roll action on button
            holder.diceButton.setOnClickListener{
                if (DiceInfo.SETTING_MODE == 0) {
                    DiceInfo.rollDice(position)
                    this.notifyItemChanged(position)
                } else {
                    DiceInfo.deleteDice(item)
                    this.notifyItemRemoved(position)
                }

            }

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