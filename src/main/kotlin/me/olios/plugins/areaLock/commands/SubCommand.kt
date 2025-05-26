package me.olios.plugins.areaLock.commands

import org.bukkit.command.CommandSender

interface SubCommand {
    fun execute(sender: CommandSender, args: Array<out String>): Boolean
    fun tabComplete(sender: CommandSender, args: Array<out String>): List<String>
}