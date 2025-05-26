package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.handlers.ConfigHandler
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AreaNewCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (args.size < 2) return false

        val playerUUID = sender.uniqueId
        val name = args[1]
        val blockType = args[2]
        val pos1 = DataHandler.getSelectionPos1(playerUUID) as Location
        val pos2 = DataHandler.getSelectionPos2(playerUUID) as Location
        val world = pos1.world

        val handler = ConfigHandler()

        handler.saveArea(name, world.name, blockType, pos1, pos2)

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        TODO("Not yet implemented")
    }
}