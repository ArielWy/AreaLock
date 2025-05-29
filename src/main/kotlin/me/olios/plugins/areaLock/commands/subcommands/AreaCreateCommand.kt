package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.handlers.ConfigHandler
import me.olios.plugins.areaLock.utils.Validator
import org.apache.commons.lang3.Validate
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AreaCreateCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (args.size < 2) return false

        val playerUUID = sender.uniqueId
        val name = args[0]
        val blockType = args[1]
        val pos1: Location = DataHandler.getSelectionPos1(playerUUID) ?: return false
        val pos2: Location = DataHandler.getSelectionPos2(playerUUID) ?: return false
        val world = pos1.world.name

        if (!Validator.validateArea(pos1, pos2, blockType)) return false

        ConfigHandler.saveArea(name, world, blockType, pos1, pos2)

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