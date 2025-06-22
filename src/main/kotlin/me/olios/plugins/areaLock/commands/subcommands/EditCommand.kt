package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.configs.DataConfigManager
import me.olios.plugins.areaLock.data.PlayerDataHandler
import me.olios.plugins.areaLock.selection.ChunkRegionHandler
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EditCommand : SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cOnly players can use this command.")
            return true
        }

        if (args.size < 2) {
            sender.sendMessage("§cUsage: /al edit <region> <name|pos1|pos2|blockType> [value]")
            return true
        }

        val regionName = args[0]
        val field = args[1].lowercase()
        val newValue = args.getOrNull(2)

        val configManager = DataConfigManager
        val region = configManager.getRegion(regionName)

        if (region == null) {
            sender.sendMessage("§cRegion '$regionName' does not exist.")
            return true
        }

        val uuid = sender.uniqueId

        when (field) {
            "name" -> {
                if (newValue == null) {
                    sender.sendMessage("§cPlease provide a new name.")
                    return true
                }
                if (configManager.renameRegion(regionName, newValue)) {
                    sender.sendMessage("§aRegion renamed to '$newValue'.")
                    ChunkRegionHandler.reloadChunks(sender)
                }
                else {
                    sender.sendMessage("§cRegion with name '$newValue' already exists.")
                    return true
                }
            }

            "pos1" -> {
                val pos = if (newValue != null) parseCoordinates(newValue) else PlayerDataHandler.getSelectionPos1(uuid)
                if (pos == null) {
                    sender.sendMessage("§cInvalid or missing position 1.")
                    return true
                }
                configManager.editRegion(regionName, pos1 = pos)
                sender.sendMessage("§aPosition 1 updated.")
            }

            "pos2" -> {
                val pos = if (newValue != null) parseCoordinates(newValue) else PlayerDataHandler.getSelectionPos2(uuid)
                if (pos == null) {
                    sender.sendMessage("§cInvalid or missing position 2.")
                    return true
                }
                configManager.editRegion(regionName, pos2 = pos)
                sender.sendMessage("§aPosition 2 updated.")
            }

            "blocktype" -> {
                if (newValue == null) {
                    sender.sendMessage("§cPlease provide a block type.")
                    return true
                }

                val material = Material.matchMaterial(newValue.uppercase())
                if (material == null || !material.isBlock) {
                    sender.sendMessage("§cInvalid block type: $newValue")
                    return true
                }

                configManager.editRegion(regionName, type = material.name)
                sender.sendMessage("§aBlock type set to ${material.name}.")
            }

            else -> {
                sender.sendMessage("§cUnknown field '$field'. Use: name, pos1, pos2, blockType")
            }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.size == 2) {
            return listOf("name", "pos1", "pos2", "blockType")
        }
        if (args.size == 3 && args[1].lowercase() == "blocktype") {
            return Material.entries
                .filter { it.isBlock }
                .map { it.name }
                .filter { it.startsWith(args[2], ignoreCase = true) }
        }
        return emptyList()
    }

    private fun parseCoordinates(input: String): Location? {
        val parts = input.split(",")
        if (parts.size != 3) return null

        return try {
            val x = parts[0].toInt()
            val y = parts[1].toInt()
            val z = parts[2].toInt()
            Location(null, x.toDouble(), y.toDouble(), z.toDouble()) // world is unused here
        } catch (e: NumberFormatException) {
            null
        }
    }
}

