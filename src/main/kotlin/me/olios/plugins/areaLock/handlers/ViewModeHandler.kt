package me.olios.plugins.areaLock.handlers

import java.util.*

object ViewModeHandler {
    private val viewEnabled: MutableSet<UUID> = mutableSetOf()

    fun toggle(uuid: UUID, enable: Boolean) {
        if (enable) viewEnabled.add(uuid) else viewEnabled.remove(uuid)
    }

    fun isEnabled(uuid: UUID): Boolean = uuid in viewEnabled
}