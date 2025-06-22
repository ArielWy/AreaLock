package me.olios.plugins.areaLock.utils.chat

enum class MessageKeys(val path: String) {
    // General Info
    AUTHOR("author"),
    VERSION("version"),
    INFO("info"), // This is an array, handled by getStringList

    // commands
    COMMAND_UNKNOWN_SUBCOMMAND("commands.unknown_subcommand"),

    // Arena Selection Commands
    COMMANDS_ARENA_SELECTION_SUCCESS_POS1("commands.arena_selection.success_pos1"),
    COMMANDS_ARENA_SELECTION_SUCCESS_POS2("commands.arena_selection.success_pos2"),
    COMMANDS_ARENA_SELECTION_INVALID_USAGE("commands.arena_selection.invalid_usage"),
    COMMANDS_ARENA_SELECTION_UNKNOWN_SUBCOMMAND("commands.arena_selection.unknown_subcommand"),
    COMMANDS_ARENA_SELECTION_NO_PERMISSION("commands.arena_selection.no_permission"),
    COMMANDS_ARENA_SELECTION_ERROR_SETTING_POS("commands.arena_selection.error_setting_pos"),

    // Arena Creation Commands
    COMMANDS_ARENA_CREATE_SUCCESS("commands.arena_create.success"),
    COMMANDS_ARENA_CREATE_INVALID_USAGE("commands.arena_create.invalid_usage"),
    COMMANDS_ARENA_CREATE_UNKNOWN_SUBCOMMAND("commands.arena_create.unknown_subcommand"),
    COMMANDS_ARENA_CREATE_NO_PERMISSION("commands.arena_create.no_permission"),
    COMMANDS_ARENA_CREATE_ERROR_CREATING("commands.arena_create.error_creating");

    // Add more as you expand your lang.json
}