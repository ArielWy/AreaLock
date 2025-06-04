package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.handlers.SelectionHandler
import me.olios.plugins.areaLock.utils.chat.MessageKeys
import me.olios.plugins.areaLock.utils.chat.sendPosMessage
import me.olios.plugins.areaLock.utils.chat.sendSimpleMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AreaSelectCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendSimpleMessage(MessageKeys.COMMANDS_ARENA_SELECTION_INVALID_USAGE)
            return true
        }

        val pos = sender.location.subtract(0.0, 1.0, 0.0)
        val playerUUID = sender.uniqueId

        if (args.isEmpty()) {
            if (SelectionHandler.setLatestSelection(playerUUID, pos))
                sender.sendPosMessage(MessageKeys.COMMANDS_ARENA_SELECTION_SUCCESS_POS1, pos.blockX, pos.blockY, pos.blockZ)
            else sender.sendPosMessage(MessageKeys.COMMANDS_ARENA_SELECTION_SUCCESS_POS2, pos.blockX, pos.blockY, pos.blockZ)
            return true
        }

        if (args[0] == "1") {
            SelectionHandler.setPos1(playerUUID, pos)
            sender.sendPosMessage(MessageKeys.COMMANDS_ARENA_SELECTION_SUCCESS_POS1, pos.blockX, pos.blockY, pos.blockZ)
            return true
        }

        if (args[0] == "2") {
            SelectionHandler.setPos2(playerUUID, pos)
            sender.sendPosMessage(MessageKeys.COMMANDS_ARENA_SELECTION_SUCCESS_POS2, pos.blockX, pos.blockY, pos.blockZ)
            return true
        }

        sender.sendSimpleMessage(MessageKeys.COMMANDS_ARENA_SELECTION_UNKNOWN_SUBCOMMAND)
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<out String>): List<String> {
        return if (args.size == 1) listOf("1", "2") else emptyList()
    }

}