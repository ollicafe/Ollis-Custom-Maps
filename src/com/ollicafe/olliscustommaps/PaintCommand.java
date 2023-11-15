package com.ollicafe.olliscustommaps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class PaintCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("paint")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length == 0){
					player.sendMessage("Wrong usage, /paint <link>");
					return true;
				}
				if(!player.getInventory().getItemInMainHand().getType().equals(Material.MAP)) {
					player.sendMessage("You need to be holding an empty map");
					return true;
				}
				
				MapView view = Bukkit.createMap(player.getWorld());
				for (MapRenderer renderer : view.getRenderers())
				    view.removeRenderer(renderer);
				
				PaintRenderer renderer = new PaintRenderer();
				if(!renderer.load(args[0])) {
					player.sendMessage("Image could not be loaded");
					return true;
				}
				view.addRenderer(renderer);
				
				ItemStack map = new ItemStack(Material.FILLED_MAP);
				MapMeta meta = (MapMeta) map.getItemMeta();
				meta.setMapView(view);
				map.setItemMeta(meta);
				
				player.getInventory().setItemInMainHand(map);
				player.sendMessage("Image Painted!");
				
				ImageManager manager = new ImageManager().getInstance();
				manager.saveImage(view.getId(), args[0]);
				
				return true;
			} else {
				sender.sendMessage("Consoles can't paint silly");
				return true;
			}
		}
		return false;
	}

}
