package com.codisimus.plugins.phatloots.addon.playernearby;

import com.codisimus.plugins.phatloots.PhatLootChest;
import com.codisimus.plugins.phatloots.events.ChestRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerNearby
  extends JavaPlugin
  implements Listener
{
  private static final long MILLIS_PER_SECOND = 1000L;
  private static int rangeSquared;
  private static long delay;
  
  public static void main(String[] args) {
    }
    
  @Override
  public void onEnable()
  {
    saveDefaultConfig();
    int range = getConfig().getInt("Range");
    rangeSquared = range * range;
    delay = getConfig().getInt("Delay") * 1000L;
    Bukkit.getPluginManager().registerEvents(this, this);
  }
  
  @EventHandler
  public void onChestRespawn(ChestRespawnEvent event)
  {
    Location loc = event.getChest().getBlock().getLocation();
    Bukkit.getOnlinePlayers().stream().filter((player) -> (player.getLocation().distanceSquared(loc) < rangeSquared)).forEachOrdered((_item) -> {
        event.setRespawnTime(System.currentTimeMillis() + delay);
      });
  }
}

