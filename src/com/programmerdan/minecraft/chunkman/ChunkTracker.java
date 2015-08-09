package com.programmerdan.minecraft.chunkman;

/**
 * Static facilities for keeping track of chunks in a threadsafe fashion. This is to allow
 *   for cool chunk management type of stuff.
 *
 * @author ProgrammerDan <programmerdan@gmail.com>
 */
public class ChunkTracker {

	/* Until I can think of a better structure, World UUID: ChunkId: Resident Nearby Count */
	private static HashMap<UUID, ChunkTracker> trackers = new HashMap<UUID, ChunkTracker>();
	
	public static ChunkTracker getTracker(Chunk chunk) {
		if (chunk != null) {
			return getTracker(chunk.getWorld());
		}
	}

	public static ChunkTracker getTracker(World world) {
		synchronized(trackers) {
			ChunkTracker ct = trackers.get(world.getUUID());
			if (ct == null) {
				ct = new ChunkTracker(world);
			}

			return ct;
		}
	}

	private UUID world;

	private HashMap<Long, Short> map;
	
	public ChunkTracker(World world) {
		this.world = world.getUUID();
		this.map = new HashMap<Long, Short>();
	}

	public void enter(Chunk...chunk) {
		synchronized(map) {
			for (Chunk c : chunk) {
				doEnter(c);
			}
		}
	}
	
	private void doEnter() {
		Long chunkId = ((long) chunk.getX() << 32L) + (long) chunk.getZ();
		Short members = map.get(chunkId);
		if (members == null) {
			members = 1;
		} else {
			members++;
		}
		map.put(chunkId, members);
	}

	public synchronized void exit(Chunk...chunk) {
		synchronized(map) {
			for (Chunk c: chunk) {
				doExit(c);
			}
		}
	}

	private void doExit() {
		Long chunkId = ((long) chunk.getX() << 32L) + (long) chunk.getZ();
		Short members = map.get(chunkId);
		if (members == null) {
			// weird, exiting a chunk that has no members.
			members = 0;
		} else {
			members--;
		}
		map.put(chunkId, members);
	}
}
