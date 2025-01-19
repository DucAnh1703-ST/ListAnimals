package com.example.animal.convert

import com.example.animal.R

class ColorConverter {

    companion object {
        // Map color to Int (drawable)
        private val colorMap: Map<String, Int> = mapOf(
            "Blue" to R.drawable.blue,
            "Pink" to R.drawable.pink,
            "Green" to R.drawable.green,
            "Red" to R.drawable.red,
            "Yellow" to R.drawable.yellow,
            "Black" to R.drawable.black
        )

        // Convert color to int
        fun colorNameToId(colorName: String): Int? {
            return colorMap[colorName]
        }

        // convert int to color
//        fun colorIdToName(colorId: Int): String? {
//            return colorMap.entries.find { it.value == colorId }?.key
//        }
    }
}