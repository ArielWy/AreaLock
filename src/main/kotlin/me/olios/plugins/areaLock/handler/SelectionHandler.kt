package me.olios.plugins.areaLock.handler

import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.data.PlayerData
import org.bukkit.Location
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SelectionHandler {

    fun setSelectionPos1(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos1(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, true)
    }

    fun setSelectionPos2(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos2(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, false)
    }
}