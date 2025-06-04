package me.olios.plugins.areaLock.utils.chat

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.olios.plugins.areaLock.AreaLock
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import java.io.File

object LangManager {
    private val plugin = AreaLock.getInstance()
    private lateinit var langConfig: JsonObject
    private val miniMessage = MiniMessage.miniMessage()

    private const val MESSAGE_NOT_FOUND_PREFIX = "§cMessage not found: "


    fun init() {
        val langFile = File(plugin.dataFolder, "lang.json")

        // Check if the lang.json file exists in the plugin's data folder.
        // If it doesn't exist, save the resource from the plugin's JAR.
        if (!langFile.exists()) {
            plugin.logger.info("lang.json not found. Saving default resource...")
            plugin.saveResource("lang.json", false) // 'false' means it won't overwrite if it exists
        }

        // Load the content of lang.json into a JsonObject.
        try {
            langFile.reader().use { reader ->
                langConfig = Gson().fromJson(reader, JsonObject::class.java)
                plugin.logger.info("lang.json loaded successfully.")
            }
        } catch (e: Exception) {
            plugin.logger.severe("Failed to load lang.json: ${e.message}")
            // Initialize with an empty object to prevent NullPointerException if loading fails
            langConfig = JsonObject()
        }
    }

    /**
     * Retrieves a string message from the loaded language configuration.
     *
     * @param path The dot-separated path to the message.
     * @return The localized string, or a "message not found" string if the key does not exist.
     * The returned string includes Bukkit color codes (e.g., '§a' for green).
     */
    fun getString(path: String, replacements: Map<String, String> = emptyMap()): Component {
        val element = getNestedElement(path)

        return if (element != null && element.isJsonPrimitive) {
            var message = element.asString
            // Apply replacements
            replacements.forEach { (key, value) ->
                message = message.replace("{$key}", value)
            }
            miniMessage.deserialize(message) // Parse MiniMessage string to Component
        } else {
            plugin.logger.warning("Attempted to get non-existent or non-string language path: $path")
            miniMessage.deserialize("$MESSAGE_NOT_FOUND_PREFIX$path")
        }
    }

    /**
     * Retrieves a list of string messages from the loaded language configuration.
     * This is useful for fields like "info" which are JSON arrays.
     * Each string in the list will have placeholders applied and be parsed into a Component.
     *
     * @param path The dot-separated path to the array (e.g., "info").
     * @param replacements A map of placeholders to be replaced in each message.
     * @return A list of Kyori Adventure Components, or an empty list if the path does not exist or is not an array.
     */
    fun getStringList(path: String, replacements: Map<String, String> = emptyMap()): List<Component> {
        val element = getNestedElement(path)

        return if (element != null && element.isJsonArray) {
            element.asJsonArray.map { jsonElement ->
                if (jsonElement.isJsonPrimitive) {
                    var message = jsonElement.asString
                    replacements.forEach { (key, value) ->
                        message = message.replace("{$key}", value)
                    }
                    miniMessage.deserialize(message)
                } else {
                    Component.empty() // Return empty component for non-primitive array elements
                }
            }
        } else {
            plugin.logger.warning("Attempted to get non-existent or non-array language path: $path")
            emptyList()
        }
    }

    /**
     * Helper function to safely navigate through nested JSON objects using dot notation.
     *
     * @param path The dot-separated path (e.g., "commands.arena_selection.success_pos1").
     * @return The JsonElement at the specified path, or null if not found.
     */
    private fun getNestedElement(path: String): JsonElement? {
        val parts = path.split('.')
        var currentElement: JsonElement? = langConfig

        for (part in parts) {
            if (currentElement == null || !currentElement.isJsonObject) {
                return null // Path segment is not an object, or currentElement is null
            }
            currentElement = currentElement.asJsonObject.get(part)
        }
        return currentElement
    }
}