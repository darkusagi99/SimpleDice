package com.gmail.darkusagi99.simpledice

import java.util.ArrayList
import java.util.HashMap
import kotlin.random.Random

object DiceInfo {

    /**
     * An array of dice items.
     */
    val ITEMS: MutableList<DiceItem> = ArrayList()

    /**
     * A map of dice items, by ID.
     */
    val ITEM_MAP: MutableMap<Int, DiceItem> = HashMap()

    private var NEXT_ID : Int = 0

    fun addDice(item: DiceItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    fun deleteDice(item: DiceItem) {
        ITEMS.remove(item)
        ITEM_MAP[item.id] = item
    }

    fun createNewDice(): DiceItem {
        NEXT_ID++
        return DiceItem(NEXT_ID, 1, 7, 0, 0)
    }

    private fun rollDice(id : Int) {
        // Get dice to roll
        val currentDice = ITEM_MAP[id]
        // Roll the dice between min and max (1 to 6 if not defined)
        val currentRoll = Random.nextInt(currentDice?.min ?:1, currentDice?.max ?:7)
        currentDice?.lastRoll = currentRoll
    }

    /**
     * An item representing a player
     */
    data class DiceItem(val id: Int, var min: Int, var max: Int, var lastRoll: Int, var shape: Int) {
        override fun toString(): String = "Id : $id - last roll : $lastRoll"
    }

    data class DiceConfigEntry(val id: Int, val name : String, val min : Int, val max : Int, var shape : Int) {

        override fun toString(): String = "Id : $id - type : $name"
    }
}