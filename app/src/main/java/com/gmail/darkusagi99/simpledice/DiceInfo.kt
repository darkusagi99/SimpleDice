package com.gmail.darkusagi99.simpledice

import kotlin.random.Random

object DiceInfo {

    /**
     * An array of dice items.
     */
    val ITEMS: MutableList<DiceItem> = ArrayList()

    /**
     * A flag for action mode
     * */
    var SETTING_MODE : Int = 0

    fun addDice(item: DiceItem): Int {
        ITEMS.add(item)
        return ITEMS.size
    }

    fun deleteDice(item: DiceItem) {
        ITEMS.remove(item)
    }

    fun createNewDice(maxValue : Int): DiceItem {

        var vShape : Int = R.drawable.ic_dice_square

        if (maxValue >= 100) {
            vShape = R.drawable.ic_dice_circle

        }

        return DiceItem(1, maxValue + 1, 0, vShape)
    }

    fun rollDice(position : Int) {
        // Get dice to roll
        val currentDice = ITEMS[position]
        // Roll the dice between min and max (1 to 6 if not defined)
        val currentRoll = Random.nextInt(currentDice.min, currentDice.max)
        currentDice.lastRoll = currentRoll
    }

    fun rollAllDices() {
        for(position in 0 until ITEMS.size) {
            rollDice(position)
        }
    }

    /**
     * An item representing a dice
     */
    data class DiceItem(var min: Int, var max: Int, var lastRoll: Int, var shape: Int) {
        override fun toString(): String = "last roll : $lastRoll"
    }
    
}