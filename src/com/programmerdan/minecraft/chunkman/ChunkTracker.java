package com.programmerdan.minecraft.chunkman;

import java.util.UUID;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.World;
import org.bukkit.Chunk;

/**
 * Static facilities for keeping track of chunks in a threadsafe fashion. This is to allow
 *   for cool chunk management type of stuff.
 *
 * @author ProgrammerDan <programmerdan@gmail.com>
 */
public class ChunkTracker {

	/* Until I can think of a better structure, World UUID: ChunkId: Resident Nearby Count */
	private static HashMap<UUID, ChunkTracker> trackers = new HashMap<UUID, ChunkTracker>();
	
	/**
	 * Gets the tracker associated with the world the passed chunk lives in.
	 *
	 * @param chunk The chunk whose world is used to find a Tracker.
	 * @return the ChunkTracker for the world containing the chunk, or null if the chunk was null.
	 */
	public static ChunkTracker getTracker(Chunk chunk) {
		if (chunk != null) {
			return getTracker(chunk.getWorld());
		}
		return null;
	}

	/**
	 * Gets the tracker associated with the world.
	 *
	 * @param world the World to use in finding a Tracker.
	 * @return the ChunkTracker for the world.
	 */
	public static ChunkTracker getTracker(World world) {
		synchronized(trackers) {
			ChunkTracker ct = trackers.get(world.getUUID());
			if (ct == null) {
				ct = new ChunkTracker(world);
				trackers.pit(world.getUUID(), ct);
			}

			return ct;
		}
	}

	private UUID world;

	/**
	 * Get the UUID of the world that this instance of ChunkTracker watches.
	 *
	 * @return the UUID of the world
	 */
	public UUID getWorld() {
		return world;
	}

	private HashMap<Long, Short> map;

	private HashSet<Long> occupied;

	Long enterEvents;
	Long exitEvents;
	Long unloadEvents;
	Long loadEvents;

	private Object lock;
	
	private ChunkTracker() {
		this.world = null;
		this.map = null;
		this.occupied = null;
		this.lock = null;
	}

	/**
	 * To get an instance of ChunkTracker, call {@see getTracker(world)}; any other technique is
	 *   not safe/recommended.
	 */
	private ChunkTracker(World world) {
		this.world = world.getUUID();
		this.map = new HashMap<Long, Short>();
		this.occupied = new HashSet<Long>();
		this.lock = new Object();
	}

	/**
	 * Record a single person entering several chunks. Calls {@see doEnter(chunk)} internally within
	 *   a synchronized wrapper.
	 *
	 * @param chunk An array of chunks being entered.
	 */
	public void enter(Chunk[] chunk) {
		synchronized(lock) {
			for (Chunk c : chunk) {
				doEnter(c);
			}
		}
	}

	/**
	 * Record a single person entering a single chunk. Calls {@see doEnter(chunk)} internally within
	 *   a synchronized wrapper.
	 *
	 * @param chunk A chunk being entered.
	 */
	public void enter(Chunk chunk) {
		synchronized(lock) {
			doEnter(chunk);
		}
	}
	
	/**
	 * You absolutely must only invoke this from a synchronized wrapper.
	 */
	private void doEnter(Chunk chunk) {
		Long chunkId = chunkId(chunk);
		Short members = map.get(chunkId);
		if (members == null) {
			members = 1;
		} else {
			members++;
		}
		map.put(chunkId, members);
		occupied.add(chunkId);
	}

	/**
	 * Record a single person leaving several chunks. Calls {@see doExit(chunk)} internally within
	 *   a synchronized wrapper.
	 *
	 * @param chunk An array of chunks being exited.
	 */
	public void exit(Chunk[] chunk) {
		synchronized(lock) {
			for (Chunk c: chunk) {
				doExit(c);
			}
		}
	}

	/**
	 * Record a single person leaving one chunk. Calls {@see doExit(chunk)} internally within 
	 *   a synchronized wrapper.
	 *
	 * @param chunk The chunk being exited.
	 */
	public void exit(Chunk chunk) {
		synchronized(lock) {
			doExit(chunk);
		}
	}

	/**
	 * You absolutely must only invoke this from a synchronized wrapper.
	 */
	private void doExit(Chunk chunk) {
		Long chunkId = chunkId(chunk);
		Short members = map.get(chunkId);
		if (members == null) {
			// weird, exiting a chunk that has no members.
			members = 0;
		} else {
			members--;
		}
		map.put(chunkId, members);
		if (members <= 0) {
			occupied.remove(chunkId);
		}
	}

	/**
	 * Override any existing value for a chunk, indicating a specific number of occupants.
	 *
	 * @param chunk The chunk to set occupancy on. Should not be null. This is not checked.
	 * @param occupants The number of occupants. Should be > 0. This is not checked.
	 */
	public void forceOccupied(Chunk chunk, Short occupants) {
		synchronized(lock) {
			Long chunkId = chunkId(chunk);
			map.put(chunkId, members);
			occupied.add(chunkId);
		}
	}

	/**
	 * Override any existing value for a chunk, indicating it is empty.
	 *
	 * @param chunk The chunk to remove occupancy on. Should not be null, This is not checked.
	 */
	public void forceEmpty(Chunk chunk) {
		synchronized(lock) {
			Long chunkId = chunkId(chunk);
			map.put(chunkId, 0);
			occupied.remove(chunkId);
		}
	}

	/**
	 * "Unloads" a chunk by removing it from the mapping. 
	 * 
	 * @param chunk The chunk to "unload". Should not be null, this is not checked.
	 */
	public void markUnloaded(Chunk chunk) {
		synchronized(lock) {
			doMarkUnloaded(c);
		}
	}

	/**
	 * "Unloads" an array of chunks by removing them from the mapping.
	 *
	 * @param chunk The array of Chunks to "unload". Should not be null, this is not checked.
	 */
	public void markUnloaded(Chunk[] chunk) {
		synchronized(lock) {
			for (Chunk c : chunk) {
				doMarkUnloaded(c);
			}
		}
	}

	/**
	 * Internal, unsynchronized method to mark a chunk unloaded. Call from synchronized contexts only.
	 */
	private void doMarkUnloaded(Chunk chunk) {
		Long chunkId = chunkId(chunk);
		map.remove(chunkId);
		occupied.remove(chunkId);
	}

	/**
	 * "Loads" a chunk by adding them to the mapping, but empty. Occupancy is unaltered.
	 *
	 * @param chunk The chunk to "load". Should not be null, this is not checked.
	 */
	public void markLoaded(Chunk chunk) {
		synchronized(lock) {
			doMarkLoaded(chunk);
		}
	}

	/**
	 * "Loads" an Array of chunks by adding them to the mapping, but empty. Occupancy is unaltered.
	 *
	 * @param chunk The array of chunks to "load". Should not be null, this is not checked.
	 */
	public void markLoaded(Chunk[] chunk) {
		synchronized(lock) {
			for (Chunk c : chunk) {
				doMarkLoaded(chunk);
			}
		}
	}

	/**
	 * Internal, unsynchronized method to mark a chunk loaded. Call from synchronized contexts only.
	 */
	private void doMarkLoaded(Chunk chunk) {
		Long chunkId = chunkId(chunk);
		if (!map.contains(chunkId)) {
			map.put(chunkId, 0);
		}
	}

	/**
	 * For a single chunk, returns if it is occupied or not. Useful for localized utilization tracking.
	 *
	 * @param chunk The chunk to test for occupancy based on tracked data.
	 * @return true if the chunk is occupied, false otherwise
	 */
	public boolean getOccupied(Chunk chunk) {
		synchronized(lock) {
			Short k = occupied.get(chunkId);
			return (k == null || k <= 0) ? false : true;
		}
	}

	/**
	 * For this world, return a count of the occupied chunks.
	 * 
	 * @return the count of occupied chunks as an integer.
	 */
	public int countOccupied() {
		synchronized(lock) {
			return occupied.size();
		}
	}

	/**
	 * For this world, return a count of the chunks that are being actively track but haven't been
	 *   marked as unloaded.
	 * 
	 * @return the count of loaded chunks (active tracking) as an integer.
	 */
	public int countLoaded() {
		synchronized(lock) {
			return map.size();
		}
	}


	/**
	 * Utility method to return the chunk's "id" based on this formula:
	 * 
	 *     <code>(chunk X << 32L) + chunk Z</code>
	 *
	 * This should be sufficient for any size Minecraft world, in practice.
	 *
	 * @return the Long single number representation of a chunk's X and Z location.
	 */
	public static Long chunkId(Chunk chunk) {
		return (chunk != null) ? ((long) chunk.getX() << 32L) + (long) chunk.getZ() : null;
	}

}
