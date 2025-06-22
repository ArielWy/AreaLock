package me.olios.plugins.regionLock.data

import java.util.*
import org.bukkit.Location
import java.util.concurrent.ConcurrentHashMap

object PlayerDataHandler {
    private val playerSelectionMap: MutableMap<UUID, PlayerSelection> = ConcurrentHashMap()

    fun setSelectionPos1(playerUUID: UUID, pos: Location) {
        playerSelectionMap.computeIfAbsent(playerUUID) { PlayerSelection() }
        playerSelectionMap[playerUUID]?.selectionPos1 = pos
    }

    fun setSelectionPos2(playerUUID: UUID, pos: Location) {
        playerSelectionMap.computeIfAbsent(playerUUID) { PlayerSelection() }
        playerSelectionMap[playerUUID]?.selectionPos2 = pos
    }

    fun setIsPos1First(playerUUID: UUID, isFirst: Boolean) {
        playerSelectionMap.computeIfAbsent(playerUUID) { PlayerSelection() }
        playerSelectionMap[playerUUID]?.isPos1First = isFirst
    }


    fun getSelectionPos1(playerUUID: UUID) =
        playerSelectionMap[playerUUID]?.selectionPos1


    fun getSelectionPos2(playerUUID: UUID) =
        playerSelectionMap[playerUUID]?.selectionPos2


    fun getIsPos1First(playerUUID: UUID) =
        playerSelectionMap[playerUUID]?.isPos1First
}