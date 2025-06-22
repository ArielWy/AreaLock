package me.olios.plugins.regionLock.data

import org.bukkit.Location

data class PlayerSelection(
    var selectionPos1: Location? = null,
    var selectionPos2: Location? = null,
    var isPos1First: Boolean = false
)
