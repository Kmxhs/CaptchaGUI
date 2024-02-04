package me.supl1na.captcha;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new CaptchaListener(), this);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	

}
