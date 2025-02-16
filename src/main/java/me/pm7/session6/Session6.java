package me.pm7.session6;

import me.pm7.session6.Commands.test;
import org.bukkit.plugin.java.JavaPlugin;

// TODO: /summon text_display ~-0.5 ~0.5 ~-2 {width:0f,billboard:"fixed",text_opacity:25,default_background:0b,shadow:0b,see_through:0b,alignment:"left",brightness:{sky:15,block:15},transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,0f,1f],translation:[0f,0f,0f],scale:[40f,2f,1f]},text:'{"text":"\\n"}',background:1694433280}

public final class Session6 extends JavaPlugin {

    private static Session6 plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        getCommand("test").setExecutor(new test());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Session6 getPlugin() {return plugin;}
}
