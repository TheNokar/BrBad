package net.plommer.BrBad.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Utils.ItemsList;
import net.plommer.BrBad.Utils.Utils;

public class DatabaseConnection {
	private BrBad plugin;
	public DatabaseConnection(BrBad plugin) {
		this.plugin = plugin;
		try {
			this.db().prepareStatement(this.getSQLTables("shop.sql")).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection db() {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + Config.mysql_host + ":" + Config.mysql_port + "/" + Config.mysql_database + "?autoReconnect=true&user=" + Config.mysql_username + "&password=" + Config.mysql_password);
		} catch (SQLException ex) {
			plugin.getLogger().info("Failed to connect to mysql. Plugin is now shutting down!");
			plugin.getServer().getPluginManager().disablePlugin(plugin);
		}
		return null;
	}
	
	public boolean addToShop(Shops shop) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("INSERT INTO `brbad_shop` (price, playerUUID, X, Y, Z, world, item_name, item_id, item_data, shop_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
			ps.setInt(1, shop.getPrice());
			ps.setString(2, shop.getPlayerUUID().toString());
			ps.setFloat(3, (float)shop.getLocation().getX());
			ps.setFloat(4, (float)shop.getLocation().getY());
			ps.setFloat(5, (float)shop.getLocation().getZ());			
			ps.setString(6, shop.getLocation().getWorld().getName());
			ps.setString(7, shop.getItem().getItemMeta().getDisplayName());
			ps.setInt(8, shop.getItem().getTypeId());
			ps.setInt(9, shop.getItem().getDurability());
			ps.setString(10, shop.getType().toString());
			return ps.executeUpdate() != 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public ArrayList<Shops> getAllShops() {
		PreparedStatement ps = null;
		ArrayList<Shops> hah = new ArrayList<Shops>();
		try {
			ps = this.db().prepareStatement("SELECT * FROM `brbad_shop` ORDER BY id");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				double[] xyz = {(double)rs.getFloat(4), (double)rs.getFloat(5), (double)rs.getFloat(6)};
				Location loc = Shops.toLocation(xyz, rs.getString(7));
				ItemStack item = new ItemStack(Material.getMaterial(rs.getInt(9)), 1, (byte) rs.getInt(10));
				item.getItemMeta().setDisplayName(rs.getString(8));
				if(!ItemsList.isCustomItem(item)) {
					item.getItemMeta().setDisplayName(null);
				}
				hah.add(new Shops(loc, item, Shops.Type.valueOf(rs.getString(11)), rs.getInt(2), Bukkit.getPlayer(UUID.fromString(rs.getString(3)))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hah;
	}
	
	private String getSQLTables(String table) {
		Scanner scan = new Scanner(plugin.getResource("sql/" + table));
		StringBuilder builder = new StringBuilder();
		while(scan.hasNextLine()) {
			builder.append(scan.nextLine());
		}
		scan.close();
		return builder.toString();
	}
	
}
