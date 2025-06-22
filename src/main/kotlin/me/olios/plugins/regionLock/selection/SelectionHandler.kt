package me.olios.plugins.regionLock.selection

import me.olios.plugins.regionLock.data.PlayerDataHandler
import org.bukkit.Location
import java.util.*

object SelectionHandler {

    fun setLatestSelection(playerUUID: UUID, location: Location): Boolean {
        val latest = PlayerDataHandler.getIsPos1First(playerUUID)

        if (latest == true) setPos2(playerUUID, location)
        else setPos1(playerUUID, location)
        return (latest == true)
    }

    fun setPos1(playerUUID: UUID, location: Location) {
        PlayerDataHandler.setSelectionPos1(playerUUID, location)
        PlayerDataHandler.setIsPos1First(playerUUID, true)
    }

    fun setPos2(playerUUID: UUID, location: Location) {
        PlayerDataHandler.setSelectionPos2(playerUUID, location)
        PlayerDataHandler.setIsPos1First(playerUUID, false)
    }

    fun getStringPos1(playerUUID: UUID): String {
        val loc = PlayerDataHandler.getSelectionPos1(playerUUID)
        return "[${loc?.x}, ${loc?.y}, ${loc?.z}]"
    }

    fun getStringPos2(playerUUID: UUID): String {
        val loc = PlayerDataHandler.getSelectionPos2(playerUUID)
        return "[${loc?.x}, ${loc?.y}, ${loc?.z}]"
    }
}