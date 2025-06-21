package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.AreaLock
import me.olios.plugins.areaLock.commands.SubCommand
import org.bukkit.command.CommandSender

class ReloadCommand() : SubCommand {
    private val plugin = AreaLock.getInstance()
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        plugin.reloadConfig()
        sender.sendMessage("§aAreaLock configuration reloaded.")
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> = emptyList()
}
