package com.lunex.mqwenauth;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.lunex.mqwenauth.listeners.PlayerListener;
import com.lunex.mqwenauth.commands.LoginCommand;
import com.lunex.mqwenauth.commands.RegisterCommand;
import com.lunex.mqwenauth.managers.AuthManager;

public class MqwenauthPlugin extends JavaPlugin {
    
    private static MqwenauthPlugin instance;
    private AuthManager authManager;
    private World lobbyWorld;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        authManager = new AuthManager(this);
        
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("register").setExecutor(new RegisterCommand(this));
        
        createLobbyWorld();
        
        getLogger().info("§c[LunexVanilla] §fMqwenauth plugini aktif edildi!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("§c[LunexVanilla] §fMqwenauth plugini devre dışı bırakıldı!");
    }
    
    private void createLobbyWorld() {
        String worldName = getConfig().getString("lobby-world", "mqwenauth_lobby");
        World world = Bukkit.getWorld(worldName);
        
        if (world == null) {
            getLogger().info("Lobby dünyası oluşturuluyor...");
            world = Bukkit.getWorldCreator(worldName)
                    .environment(World.Environment.NORMAL)
                    .createWorld();
            
            if (world != null) {
                world.setAutoSave(false);
                getLogger().info("Lobby dünyası oluşturuldu: " + worldName);
            }
        }
        
        lobbyWorld = world;
    }
    
    public static MqwenauthPlugin getInstance() {
        return instance;
    }
    
    public AuthManager getAuthManager() {
        return authManager;
    }
