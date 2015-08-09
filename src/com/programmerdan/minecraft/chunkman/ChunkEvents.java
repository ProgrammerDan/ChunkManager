package com.programmerdan.minecraft.chunkman;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.

/**
 * Holds all the event handlers dealing with events that have chunk load and unload implications
 * 
 * @author ProgrammerDan <programmerdan@gmail.com>
 */
class ChunkEvents implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorAndReactChunkLoad(ChunkLoadEvent event) {

	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorAndReactChunkUnload(ChunkUnloadEvent event) {

	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerLogin(PlayerLoginEvent event) {

	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerJoin(PlayerJoinEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerQuit(PlayerQuitEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerKick(PlayerKickEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerChangeWorld(PlayerChangedWorldEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerRespawn(PlayerRespawnEvent event) {
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerMovement(PlayerMoveEvent event) {
	}

}
