package com.lunex.mqwenauth.commands;

import com.lunex.mqwenauth.MqwenauthPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {
    
    private MqwenauthPlugin plugin;
    
    public LoginCommand(MqwenauthPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cBu komut sadece oyuncular tarafından kullanılabilir!");
            return true;
        }
        
        Player player = (Player) sender;
        String playerName = player.getName();
        
        if (args.length != 1) {
            player.sendMessage("§c❌ Kullanım: /login <şifre>");
            return true;
        }
        
        if (plugin.getAuthManager().isAuthenticated(player)) {
            player.sendMessage("§c❌ Zaten giriş yapmışsınız!");
            return true;
        }
        
        String password = args[0];
        
        if (plugin.getAuthManager().login(playerName, password)) {
            plugin.getAuthManager().setAuthenticated(player);
            
            Location spawn = Bukkit.getWorld("world").getSpawnLocation();
            player.teleport(spawn);
            
            player.sendMessage("§c════════════════════════════════════════");
            player.sendMessage("§c╔════════════ LunexVanilla ════════════╗");
            player.sendMessage("§c║");
            player.sendMessage("§c║  ✓ §fGiriş başarılı!");
            player.sendMessage("§c║");
            player.sendMessage("§c║  §fHoş geldiniz, §c" + playerName);
            player.sendMessage("§c║");
            player.sendMessage("§c╚════════════════════════════════════════╝");
            player.sendMessage("§c════════════════════════════════════════");
            
            Bukkit.broadcastMessage("§c[LunexVanilla] §f" + playerName + " §cgiriş yaptı!");
            
        } else {
            player.sendMessage("§c════════════════════════════════════════");
            player.sendMessage("§c╔════════════ LunexVanilla ════════════╗");
            player.sendMessage("§c║");
            player.sendMessage("§c║  ❌ §fŞifre hatalı!");
            player.sendMessage("§c║");
            player.sendMessage("§c║  §cLütfen tekrar deneyin.");
            player.sendMessage("§c║");
            player.sendMessage("§c╚════════════════════════════════════════╝");
            player.sendMessage("§c════════════════════════════════════════");
        }
        
        return true;
    }
}
