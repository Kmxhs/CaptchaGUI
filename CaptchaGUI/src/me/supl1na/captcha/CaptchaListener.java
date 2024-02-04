package me.supl1na.captcha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CaptchaListener implements Listener {

	private List<String> okay = new ArrayList<>();

	private static int getRandomNumberInRange(int min, int max) {
		if (min >= max) { throw new IllegalArgumentException("max must be greater than min"); }

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		okay.remove(player.getName());

		if(!okay.contains(event.getPlayer().getName())) {
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
				
				@Override
				public void run() {
					
					Inventory inv = Bukkit.createInventory(null, 9, "Captcha");

					ItemStack captcha = new ItemStack(Material.EMERALD_BLOCK);
					ItemMeta captchameta = captcha.getItemMeta();
					captchameta.setDisplayName(ChatColor.GREEN + "¡Click here!");
					captcha.setItemMeta(captchameta);

					inv.setItem(getRandomNumberInRange(1, 7), captcha);
					
					event.getPlayer().openInventory(inv);
				}
			}, 20);
		}

	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(event.getInventory().getTitle().equalsIgnoreCase("Captcha")) {
			if(!okay.contains(event.getPlayer().getName())) {
				((Player) event.getPlayer()).kickPlayer("¡Nope!");
			} else {
				return;
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		if(event.getInventory().getTitle().equalsIgnoreCase("Captcha")) {
			if(player.getGameMode() != GameMode.CREATIVE) event.setCancelled(true);

			if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "¡Click here!")) {
				okay.add(player.getName());
				player.closeInventory();
			} else {
				okay.remove(player.getName());
				player.kickPlayer("¡Nope!");
			}
		}
	}

}
