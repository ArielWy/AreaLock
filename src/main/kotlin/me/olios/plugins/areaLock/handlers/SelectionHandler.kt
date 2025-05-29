package me.olios.plugins.areaLock.handlers

import me.olios.plugins.areaLock.data.DataHandler
import org.bukkit.Location
import java.util.*

object SelectionHandler {

    fun setLatestSelection(playerUUID: UUID, location: Location) {
        val latest = DataHandler.getIsPos1First(playerUUID)

        if (latest == true) setPos2(playerUUID, location)
        else setPos1(playerUUID, location)
    }

    fun setPos1(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos1(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, true)
    }

    fun setPos2(playerUUID: UUID, location: Location) {
        DataHandler.setSelectionPos2(playerUUID, location)
        DataHandler.setIsPos1First(playerUUID, false)
    }

    fun getStringPos1(playerUUID: UUID): String {
        val loc = DataHandler.getSelectionPos1(playerUUID)
        return "[${loc?.x}, ${loc?.y}, ${loc?.z}]"
    }

    fun getStringPos2(playerUUID: UUID): String {
        val loc = DataHandler.getSelectionPos2(playerUUID)
        return "[${loc?.x}, ${loc?.y}, ${loc?.z}]"
    }
}