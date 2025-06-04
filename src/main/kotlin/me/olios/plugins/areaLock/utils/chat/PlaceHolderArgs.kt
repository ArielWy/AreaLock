package me.olios.plugins.areaLock.utils.chat


// Converts the placeholder arguments into a map suitable for LangManager.
sealed interface MessagePlaceholderArgs {
    fun toMap(): Map<String, String>
}


// Represents arguments for messages that require X, Y, Z coordinates.
data class PosArgs(val posX: Int, val posY: Int, val posZ: Int) : MessagePlaceholderArgs {
    override fun toMap(): Map<String, String> = mapOf(
        "posX" to posX.toString(),
        "posY" to posY.toString(),
        "posZ" to posZ.toString()
    )
}


 // Represents arguments for messages related to arena creation.
data class ArenaCreateArgs(val name: String, val blockType: String, val world: String) : MessagePlaceholderArgs {
    override fun toMap(): Map<String, String> = mapOf(
        "name" to name,
        "blockType" to blockType,
        "world" to world
    )
}


// A placeholder type for messages that do not require any dynamic replacements.
data object NoArgs : MessagePlaceholderArgs {
    override fun toMap(): Map<String, String> = emptyMap()
}