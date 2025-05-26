package me.olios.plugins.areaLock.data

import org.bukkit.Location

data class PlayerData(
    var selectionPos1: Location? = null,
    var selectionPos2: Location? = null,
    var isPos1First: Boolean = false
)
