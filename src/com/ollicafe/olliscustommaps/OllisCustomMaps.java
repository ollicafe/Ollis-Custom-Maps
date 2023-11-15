package com.ollicafe.olliscustommaps;

import org.bukkit.plugin.java.JavaPlugin;

public class OllisCustomMaps extends JavaPlugin {
	
	@Override
	public void onEnable() {
		ImageManager manager = new ImageManager().getInstance();
		manager.init();
		
		this.getCommand("paint").setExecutor(new PaintCommand());
		
	}
	
	@Override
	public void onDisable() {
		
	}

}
