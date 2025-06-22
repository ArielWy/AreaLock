package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.configs.DataConfigManager
import me.olios.plugins.areaLock.handlers.RegionChunkHandler
import me.olios.plugins.areaLock.utils.Validator
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§cThis command can only be used by players.")
            return true
        }

        if (args.size < 2) {
            sender.sendMessage("§cUsage: /al create <name> <blockType>")
            return true
        }

        val playerUUID = sender.uniqueId
        val name = args[0]
        val blockType = args[1]

        val pos1 = DataHandler.getSelectionPos1(playerUUID)
        val pos2 = DataHandler.getSelectionPos2(playerUUID)

        if (pos1 == null || pos2 == null) {
            sender.sendMessage("§cYou must select two positions first with /al select 1 and 2.")
            return true
        }

        if (!Validator.validateArea(pos1, pos2, blockType)) {
            sender.sendMessage("§cInvalid region or block type.")
            return true
        }

        val world = pos1.world.name
        DataConfigManager.saveArea(name, world, blockType, pos1, pos2)

        sender.sendMessage("§aRegion '$name' saved successfully.")
        RegionChunkHandler.checkLoadedChunks(sender)
        return true
    }


    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        if (args.size == 2)
            return Material.entries.map { it.name }.toList()
                .filter { it.startsWith(args[1], ignoreCase = true) }
                .toMutableList()

        return emptyList()
    }
}