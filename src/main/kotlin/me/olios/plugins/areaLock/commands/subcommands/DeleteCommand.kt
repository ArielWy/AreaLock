package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.configs.DataConfigManager
import me.olios.plugins.areaLock.handlers.RegionChunkHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DeleteCommand() : SubCommand {
    private val configManager = DataConfigManager
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (!sender.hasPermission("arealock.admin")) {
            sender.sendMessage("§cYou don't have permission to do that.")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /al delete <region>")
            return true
        }

        val regionName = args[0]

        val region = configManager.getRegion(regionName)
        if (region == null) {
            sender.sendMessage("§cRegion '$regionName' does not exist.")
            return true
        }

        if (configManager.deleteRegion(regionName)) {
            sender.sendMessage("§aRegion '$regionName' has been deleted successfully.")
            if (sender is Player) RegionChunkHandler.checkLoadedChunks(sender)
        } else {
            sender.sendMessage("§cFailed to delete region '$regionName'.")
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.size == 1) {
            configManager.getAllRegions().keys.filter { it.startsWith(args[0]) }
        } else emptyList()
    }
}
