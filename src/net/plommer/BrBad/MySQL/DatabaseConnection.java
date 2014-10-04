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
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.plommer.BrBad.BrBad;
import net.plommer.BrBad.Configs.Config;
import net.plommer.BrBad.CopStick.CopSafeZone;
import net.plommer.BrBad.Shop.Shops;
import net.plommer.BrBad.Shop.ShopsUtils;
import net.plommer.BrBad.Utils.ItemsList;

public class DatabaseConnection {
	private BrBad plugin;
	public DatabaseConnection(BrBad plugin) {
		this.plugin = plugin;
		try {
			this.db().prepareStatement(this.getSQLTables("shop.sql")).execute();
			this.db().prepareStatement(this.getSQLTables("shopslog.sql")).execute();
			this.db().prepareStatement(this.getSQLTables("cops.sql")).execute();
			this.db().prepareStatement(this.getSQLTables("zone.sql")).execute();
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
			ps = this.db().prepareStatement("INSERT INTO `brbad_shop` (price, amount, playerUUID, X, Y, Z, world, item_name, item_id, item_data, shop_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
			ps.setInt(1, shop.getPrice());
			ps.setInt(2, shop.getAmount());
			ps.setString(3, shop.getPlayerUUID().toString());
			ps.setFloat(4, (float)shop.getLocation().getX());
			ps.setFloat(5, (float)shop.getLocation().getY());
			ps.setFloat(6, (float)shop.getLocation().getZ());			
			ps.setString(7, shop.getLocation().getWorld().getName());
			ps.setString(8, shop.getItem().getItemMeta().getDisplayName());
			ps.setInt(9, shop.getItem().getTypeId());
			ps.setInt(10, shop.getItem().getDurability());
			ps.setString(11, shop.getType().toString());
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
	
	public boolean addShopLog(Shops shop, Player player) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("INSERT INTO `brbad_shoplog` (price, amount, playerUUID, item_name, item_id, item_data, shop_type) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, shop.getPrice());
			ps.setInt(2, shop.getAmount());
			ps.setString(3, player.getUniqueId().toString());
			ps.setString(4, shop.getItem().getItemMeta().getDisplayName());
			ps.setInt(5, shop.getItem().getTypeId());
			ps.setInt(6, shop.getItem().getDurability());
			ps.setString(7, shop.getType().toString());
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
	
	public boolean addCopLog(Player player, Player owner) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("INSERT INTO `brbad_cops` (playerUUID, copUUID) VALUES (?, ?)");
			ps.setString(1, player.getUniqueId().toString());
			ps.setString(2, owner.getUniqueId().toString());
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
	
	public boolean addZone(Location loc, Location loc2) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("INSERT INTO `brbad_zone` (X, Y, Z, X2, Y2, Z2, world) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setFloat(1, (float)loc.getX());
			ps.setFloat(2, (float)loc.getY());
			ps.setFloat(3, (float)loc.getZ());
			ps.setFloat(4, (float)loc2.getX());
			ps.setFloat(5, (float)loc2.getY());
			ps.setFloat(6, (float)loc2.getZ());
			ps.setString(7, loc.getWorld().getName());
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
	
	public ArrayList<CopSafeZone> getZones() {
		PreparedStatement ps = null;
		ArrayList<CopSafeZone> hah = new ArrayList<CopSafeZone>();
		try {
			ps = this.db().prepareStatement("SELECT * FROM `brbad_zone` ORDER BY id");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				double[] xyz = {(double)rs.getFloat(2), (double)rs.getFloat(3), (double)rs.getFloat(4)};
				double[] xyz2 = {(double)rs.getFloat(5), (double)rs.getFloat(6), (double)rs.getFloat(7)};
				Location loc = ShopsUtils.toLocation(xyz, rs.getString(8));
				Location loc2 = ShopsUtils.toLocation(xyz2, rs.getString(8));
				hah.add(new CopSafeZone(loc, loc2));
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
	
	public boolean removeShop(Shops shop) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("DELETE FROM `brbad_shop` WHERE X = (?) AND Y = (?) AND Z = (?) AND world = (?)");
			ps.setFloat(1, (float) shop.getLocation().getX());
			ps.setFloat(2, (float) shop.getLocation().getY());
			ps.setFloat(3, (float) shop.getLocation().getZ());
			ps.setString(4, shop.getLocation().getWorld().getName());
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
				double[] xyz = {(double)rs.getFloat(5), (double)rs.getFloat(6), (double)rs.getFloat(7)};
				Location loc = ShopsUtils.toLocation(xyz, rs.getString(8));
				ItemStack item = new ItemStack(Material.getMaterial(rs.getInt(10)), 1, (byte) rs.getInt(11));
				if(ItemsList.isCustomItemByName(rs.getString(9)) != null) {
					item = ItemsList.isCustomItemByName(rs.getString(9));
				}
				hah.add(new Shops(loc, item, Shops.Type.valueOf(rs.getString(12).toUpperCase()), rs.getInt(2), rs.getInt(3), Bukkit.getPlayer(UUID.fromString(rs.getString(4)))));
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
	
	public Shops getShopAtLocation(Location loc) {
		PreparedStatement ps = null;
		try {
			ps = this.db().prepareStatement("SELECT * FROM `brbad_shop` where X = (?) AND Y = (?) AND Z = (?) AND world = (?)");
			ps.setFloat(1, (float)loc.getX());
			ps.setFloat(2, (float)loc.getY());
			ps.setFloat(3, (float)loc.getZ());
			ps.setString(4, loc.getWorld().getName());
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				double[] xyz = {(double)rs.getFloat(5), (double)rs.getFloat(6), (double)rs.getFloat(7)};
				Location loc2 = ShopsUtils.toLocation(xyz, rs.getString(8));
				ItemStack item = new ItemStack(Material.getMaterial(rs.getInt(10)), 1, (byte) rs.getInt(11));
				if(ItemsList.isCustomItemByName(rs.getString(9)) != null) {
					item = ItemsList.isCustomItemByName(rs.getString(9));
				}
				return new Shops(loc2, item, Shops.Type.valueOf(rs.getString(12).toUpperCase()), rs.getInt(2), rs.getInt(3), Bukkit.getPlayer(UUID.fromString(rs.getString(4))));
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
		return null;
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
