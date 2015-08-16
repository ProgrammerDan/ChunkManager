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
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorChunkLoad(ChunkLoadEvent event) {
		// update load
		ChunkTracker ct = ChunkTracker.getTracker(event.getWorld());
		ct.markLoaded(event.getChunk());
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorChunkUnload(ChunkUnloadEvent event) {
		// update unload
		if (!event.isCancelled()) {
			ChunkTracker ct = ChunkTracker.getTracker(event.getWorld()
			ct.markUnloaded(event.getChunk());
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerLogin(PlayerLoginEvent event) {
		// update entrance
		// Login Event 
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorPlayerJoin(PlayerJoinEvent event) {
		// update entrance
		Player p = event.getPlayer();
		if (p != null) {
			Location l = p.getLocation();
			ChunkTracker ct = ChunkTracker.getTracker(l.getWorld());
			ct.enter(l.getChunk());
		}
	}

	private final Set<UUID> kickList = new HashSet<UUID>();

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerQuit(PlayerQuitEvent event) {
		// update exit
		Player p = event.getPlayer();
		if (p != null) {
			if (!kickList.contains(p.getUUID())) {
				Location l = p.getLocation();
				ChunkTracker ct = ChunkTracker.getTracker(l.getWorld());
				ct.exit(l.getChunk());
			} else {
				kickList.remove(p.getUUID());
			}
		}
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorPlayerKick(PlayerKickEvent event) {
		// update exit
		if (!event.isCancelled()) {
			Player p = event.getPlayer();
			if (p != null) {
				Location l = p.getLocation();
				ChunkTracker ct = ChunkTracker.getTracker(l.getWorld());
				ct.exit(l.getChunk());
				kickList.add(p.getUUID());
			}
		}
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorPlayerTeleport(PlayerTeleportEvent event) {
		// update from and to chunks
		if (!event.isCancelled()) {
			Location f = event.getFrom();
			ChunkTracker ct = ChunkTracker.getTracker(f.getWorld());
			ct.exit(f.getChunk());
			Location t = event.getTo();
			ct = ChunkTracker.getTracker(t.getWorld());
			ct.enter(t.getChunk());
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
	public void monitorPlayerChangeWorld(PlayerChangedWorldEvent event) {
		// update entrance and exit chunk
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorDeathEvent(PlayerDeathEvent event) {
		// update exit chunk
		Player p = event.getEntity();
		if (p != null) {
			Location l = p.getLocation();
			ChunkTracker ct = ChunkTracker.getTracker(l.getWorld());
			ct.exit(l.getChunk());
		}
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorPlayerRespawn(PlayerRespawnEvent event) {
		// update entrance chunk
		Location l = event.getRespawnLocation();
		if (l != null) {
			ChunkTracker ct = ChunkTracker.getTracker(l.getWorld());
			ct.enter(l.getChunk());
		}
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
	public void monitorPlayerMovement(PlayerMoveEvent event) {
		// update entrance and exit chunk
		if (!event.isCancelled()) {
			Location f = event.getFrom();
			ChunkTracker ct = ChunkTracker.getTracker(f.getWorld());
			ct.exit(f.getChunk());
			Location t = event.getTo();
			ct = ChunkTracker.getTracker(t.getWorld());
			ct.enter(t.getChunk());
		}
	}

}

