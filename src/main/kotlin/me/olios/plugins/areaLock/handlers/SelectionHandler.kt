package me.olios.plugins.areaLock.handlers

import me.olios.plugins.areaLock.data.DataHandler
import org.bukkit.Location
import java.util.*

class SelectionHandler {

    fun setLatestSelection(playerUUID: UUID, location: Location) {
        val latest = DataHandler.getIsPos1First(playerUUID)

        if (latest == true) setSelectionPos2(playerUUID, location)
        else setSelectionPos1(playerUUID, location)
    }

    fun setSelectionPos1(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos1(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, true)
    }

    fun setSelectionPos2(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos2(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, false)
    }
}