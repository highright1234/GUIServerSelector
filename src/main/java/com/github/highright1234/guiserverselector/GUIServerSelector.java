package com.github.highright1234.guiserverselector;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GUIServerSelector extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("GuiServerSelector")).setExecutor(new SelectorCommand(this));
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
