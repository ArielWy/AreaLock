package me.olios.plugins.areaLock.utils.chat

import me.olios.plugins.areaLock.AreaLock
import me.olios.plugins.areaLock.utils.chat.ChatUtils.sendComponent
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

object ChatUtils {
    private val plugin = AreaLock.getInstance()
    private val audience: BukkitAudiences = BukkitAudiences.create(plugin)

    // Sends a Kyori Adventure Component to the CommandSender.
    fun CommandSender.sendComponent(component: Component) {
        audience.sender(this).sendMessage(component)
    }
}

/**
 * Generic extension function to send a localized message using a MessageKey
 * and a general MessagePlaceholderArgs object.
 * This is the underlying method that specific helpers will call.
 *
 * @param key The MessageKeys enum entry for the desired message.
 * @param args The MessagePlaceholderArgs object containing the replacements. Defaults to NoArgs if not provided.
 */
fun CommandSender.sendLangMessage(key: MessageKeys, args: MessagePlaceholderArgs = NoArgs) {
    val component = LangManager.getString(key.path, args.toMap())
    this.sendComponent(component)
}

/**
 * Sends a message that requires X, Y, Z coordinates as placeholders.
 *
 * @param key The MessageKeys enum entry (e.g., COMMANDS_ARENA_SELECTION_SUCCESS_POS1).
 * @param posX The X coordinate.
 * @param posY The Y coordinate.
 * @param posZ The Z coordinate.
 */
fun CommandSender.sendPosMessage(key: MessageKeys, posX: Int, posY: Int, posZ: Int) {
    // You can add a runtime check here if you want to ensure the key *actually* expects these placeholders.
    // For compile-time safety, rely on how you call this function.
    this.sendLangMessage(key, PosArgs(posX, posY, posZ))
}

/**
 * Sends a message that requires arena creation details as placeholders.
 *
 * @param key The MessageKeys enum entry (e.g., COMMANDS_ARENA_CREATE_SUCCESS).
 * @param name The arena name.
 * @param blockType The block type.
 * @param world The world name.
 */
fun CommandSender.sendArenaCreateMessage(key: MessageKeys, name: String, blockType: String, world: String) {
    this.sendLangMessage(key, ArenaCreateArgs(name, blockType, world))
}

fun CommandSender.sendUnknownCommandMessage(subCommand: String) {
    this.sendLangMessage(MessageKeys.COMMAND_UNKNOWN_SUBCOMMAND, UnknownSubCommand(subCommand))
}

/**
 * Helper function for sending general info messages (from the "info" array).
 * No specific placeholders are expected for the "info" array itself,
 * but if individual info strings had placeholders, they'd be handled by LangManager.
 */
fun CommandSender.sendInfo() {
    LangManager.getStringList(MessageKeys.INFO.path).forEach { component ->
        this.sendComponent(component)
    }
}

/**
 * Helper function for sending simple messages with no specific placeholders.
 * E.g., invalid usage, unknown subcommand, no permission.
 */
fun CommandSender.sendSimpleMessage(key: MessageKeys) {
    this.sendLangMessage(key, NoArgs)
}