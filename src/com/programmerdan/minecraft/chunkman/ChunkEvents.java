package com.programmerdan.minecraft.chunkman;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Holds all the event handlers dealing with events that have chunk load and unload implications
 * 
 * @author ProgrammerDan <programmerdan@gmail.com>
 */
class ChunkEvents implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorAndReactChunkLoad(ChunkLoadEvent event) {
		// update load
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorAndReactChunkUnload(ChunkUnloadEvent event) {
		// update unload
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		// update entrance
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerLogin(PlayerLoginEvent event) {
		// update entrance
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerJoin(PlayerJoinEvent event) {
		// update entrance
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerQuit(PlayerQuitEvent event) {
		// update exit
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerKick(PlayerKickEvent event) {
		// update exit
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerChangeWorld(PlayerChangedWorldEvent event) {
		// update entrance and exit chunk
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerRespawn(PlayerRespawnEvent event) {
		// update entrance and exit chunk
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerMovement(PlayerMoveEvent event) {
		// update entrance and exit chunk
	}

}

