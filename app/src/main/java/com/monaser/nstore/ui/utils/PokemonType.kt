package com.monaser.nstore.ui.utils

import androidx.compose.ui.graphics.Color
import com.monaser.nstore.ui.theme.bug
import com.monaser.nstore.ui.theme.dark
import com.monaser.nstore.ui.theme.dragon
import com.monaser.nstore.ui.theme.electric
import com.monaser.nstore.ui.theme.fairy
import com.monaser.nstore.ui.theme.fighting
import com.monaser.nstore.ui.theme.fire
import com.monaser.nstore.ui.theme.flying
import com.monaser.nstore.ui.theme.ghost
import com.monaser.nstore.ui.theme.grass
import com.monaser.nstore.ui.theme.gray_21
import com.monaser.nstore.ui.theme.ground
import com.monaser.nstore.ui.theme.ice
import com.monaser.nstore.ui.theme.poison
import com.monaser.nstore.ui.theme.psychic
import com.monaser.nstore.ui.theme.rock
import com.monaser.nstore.ui.theme.steel
import com.monaser.nstore.ui.theme.water

object PokemonType {

    fun getTypeColor(type: String): Color {
        return when (type) {
            "fighting" -> fighting
            "flying" -> flying
            "poison" -> poison
            "ground" -> ground
            "rock" -> rock
            "bug" -> bug
            "ghost" -> ghost
            "steel" -> steel
            "fire" -> fire
            "water" -> water
            "grass" -> grass
            "electric" -> electric
            "psychic" -> psychic
            "ice" -> ice
            "dragon" -> dragon
            "fairy" -> fairy
            "dark" -> dark
            else -> gray_21
        }
    }
}