package me.olios.plugins.areaLock.commands.subcommands

import me.olios.plugins.areaLock.commands.SubCommand
import me.olios.plugins.areaLock.data.DataHandler
import me.olios.plugins.areaLock.configs.DataManager
import me.olios.plugins.areaLock.utils.Validator
import me.olios.plugins.areaLock.utils.chat.MessageKeys
import me.olios.plugins.areaLock.utils.chat.sendArenaCreateMessage
import me.olios.plugins.areaLock.utils.chat.sendSimpleMessage
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AreaCreateCommand: SubCommand {
    override fun execute(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendSimpleMessage(MessageKeys.COMMANDS_ARENA_CREATE_INVALID_USAGE)
            return true
        }

        if (args.size < 2) {
            sender.sendSimpleMessage(MessageKeys.COMMANDS_ARENA_CREATE_UNKNOWN_SUBCOMMAND)
            return true
        }

        val playerUUID = sender.uniqueId
        val name = args[0]
        val blockType = args[1]

        val pos1: Location? = DataHandler.getSelectionPos1(playerUUID)
        val pos2: Location? = DataHandler.getSelectionPos2(playerUUID)

        if (pos1 == null || pos2 == null) {
            return true
        }

        val world = pos1.world.name

        if (!Validator.validateArea(pos1, pos2, blockType)) {
            sender.sendArenaCreateMessage(MessageKeys.COMMANDS_ARENA_CREATE_ERROR_CREATING, name, blockType, world)
            return true
        }

        DataManager.saveArea(name, world, blockType, pos1, pos2)
        sender.sendArenaCreateMessage(MessageKeys.COMMANDS_ARENA_CREATE_SUCCESS, name, blockType, world)

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