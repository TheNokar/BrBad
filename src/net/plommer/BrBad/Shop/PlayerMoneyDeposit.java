package net.plommer.BrBad.Shop;

import net.plommer.BrBad.Utils.SetupVault;

import org.bukkit.entity.Player;

public class PlayerMoneyDeposit {

	public static void removeMoney(Player player, int amount) {
		SetupVault.economy.withdrawPlayer(player.getName(), amount);
	}
	
	public static void addMoney(Player player, int amount) {
		SetupVault.economy.depositPlayer(player.getName(), amount);
	}

	public static boolean hasMoney(Player player, int money) {
		if(SetupVault.economy.getBalance(player.getName()) >= money) {
			return true;
		}
		return false;
	}
	
}
