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

    /**
     * List of number of face by shapes
     * */
    var TRIANGLE_LIST : List<Int> =  listOf(4, 8, 20, 23, 60)
    var PENTAGON_LIST : List<Int> =  listOf(10, 17, 19, 22, 26, 28)
    var DIAMOND_LIST : List<Int> =  listOf(5, 12, 24, 27, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59)
    var KITE_LIST : List<Int> =  listOf(7, 25, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47)
    var HEXAGON_LIST : List<Int> =  listOf(13, 14, 15)

    fun addDice(item: DiceItem): Int {
        ITEMS.add(item)
        return ITEMS.size
    }

    fun deleteDice(item: DiceItem) {
        ITEMS.remove(item)
    }

    fun createNewDice(maxValue : Int): DiceItem {

        val vShape: Int

        when {

            // 2 -> Cercle
            (maxValue == 2) -> vShape = R.drawable.ic_dice_circle

            // 4, 8 et 20, 23, 60 -> Triangle
            (TRIANGLE_LIST.contains(maxValue)) -> vShape = R.drawable.ic_dice_triangle

            // 10, 17, 19, 22, 26, 28 -> Pentagone
            (PENTAGON_LIST.contains(maxValue)) -> vShape = R.drawable.ic_dice_triangle

            // 5, 12, 24, 27 48 à 60 -> Diamant
            (DIAMOND_LIST.contains(maxValue)) -> vShape = R.drawable.ic_dice_triangle

            // 7, 25, 30 à 47 -> Losange
            (KITE_LIST.contains(maxValue)) -> vShape = R.drawable.ic_dice_triangle

            // 13, 14, 15 -> Hexagone
            (HEXAGON_LIST.contains(maxValue)) -> vShape = R.drawable.ic_dice_triangle

            // Nbe faces > 60 -> Cercle
            (maxValue > 60) -> vShape = R.drawable.ic_dice_circle
            // Sinon -> Carré (3, 6, 9, 16, 18, 21)
            else -> vShape = R.drawable.ic_dice_square
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