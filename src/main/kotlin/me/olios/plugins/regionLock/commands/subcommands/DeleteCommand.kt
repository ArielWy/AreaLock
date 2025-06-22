package me.olios.plugins.regionLock.commands.subcommands

import me.olios.plugins.regionLock.commands.SubCommand
import me.olios.plugins.regionLock.configs.DataConfigManager
import me.olios.plugins.regionLock.selection.ChunkRegionHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DeleteCommand() : SubCommand {
    private val configManager = DataConfigManager
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (!sender.hasPermission("RegionLock.admin")) {
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
            if (sender is Player) ChunkRegionHandler.reloadChunks(sender)
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
