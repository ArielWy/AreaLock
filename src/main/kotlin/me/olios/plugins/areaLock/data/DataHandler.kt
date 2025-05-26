package me.olios.plugins.areaLock.data

import java.util.*
import org.bukkit.Location
import java.util.concurrent.ConcurrentHashMap

object DataHandler {
    private val playerDataMap: MutableMap<UUID, PlayerData> = ConcurrentHashMap()

    fun setSelectionPos1(playerUUID: UUID, pos: Location) {
        playerDataMap[playerUUID]?.selectionPos1 = pos
    }

    fun setSelectionPos2(playerUUID: UUID, pos: Location) {
        playerDataMap[playerUUID]?.selectionPos2 = pos
    }

    fun setIsPos1First(playerUUID: UUID, isFirst: Boolean) {
        playerDataMap[playerUUID]?.isPos1First = isFirst
    }

    fun getSelectionPos1(playerUUID: UUID) =
        playerDataMap[playerUUID]?.selectionPos1


    fun getSelectionPos2(playerUUID: UUID) =
        playerDataMap[playerUUID]?.selectionPos2


    fun getIsPos1First(playerUUID: UUID) =
        playerDataMap[playerUUID]?.isPos1First
}