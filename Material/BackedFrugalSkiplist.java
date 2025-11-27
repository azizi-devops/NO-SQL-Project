// package ...;   // <-- IMPORTANT: copy from VersionList.java if present

import com.fasterxml.jackson.databind.ObjectMapper;

public class BackedFrugalSkiplist<P> implements VersionList<P> {

    // ---------------- NODE RECORD STORED IN KVSTORE ----------------
    private static class NodeRecord {
        public long timestamp;
        public int level;
        public String payload;     // serialized P
        public String nextKey;     // key of next (older) node
        public String vRidgyKey;   // key of skip pointer

        public NodeRecord() {}     // required by Jackson
    }

    // ---------------- FIELDS ----------------
    private final KVStore store;
    private final Serializer<P> serializer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final java.util.Random rnd = new java.util.Random();

    private String headKey = null; // key of the newest node


    // ---------------- CONSTRUCTOR ----------------
    public BackedFrugalSkiplist(KVStore store, Serializer<P> serializer) {
        this.store = store;
        this.serializer = serializer;
    }


    // ---------------- RANDOM LEVEL ----------------
    private int randomLevel() {
        int lvl = 0;
        while (rnd.nextBoolean()) {
            lvl++;
        }
        return lvl;
    }


    // ---------------- LOAD/SAVE HELPERS ----------------
    private NodeRecord load(String key) throws Exception {
        if (key == null) return null;
        String json = store.get(key);
        if (json == null) return null;
        return mapper.readValue(json, NodeRecord.class);
    }

    private void save(String key, NodeRecord rec) throws Exception {
        String json = mapper.writeValueAsString(rec);
        store.put(key, json);
    }


    // ---------------- APPEND ----------------
    @Override
    public void append(P p, long timestamp) {
        try {
            int lvl = randomLevel();

            String oldHeadKey = headKey;

            // create new record
            NodeRecord rec = new NodeRecord();
            rec.timestamp = timestamp;
            rec.level = lvl;
            rec.payload = serializer.serialize(p);
            rec.nextKey = oldHeadKey;
            rec.vRidgyKey = null;

            // set vRidgyKey: find first node with higher level
            String cursorKey = oldHeadKey;
            while (cursorKey != null) {
                NodeRecord c = load(cursorKey);
                if (c == null) break;

                if (c.level > lvl) {
                    rec.vRidgyKey = cursorKey;
                    break;
                }

                cursorKey = c.nextKey;
            }

            // key for storing this version
            String key = Long.toString(timestamp);

            // save in KV store
            save(key, rec);

            // update head
            headKey = key;

        } catch (Exception e) {
            throw new RuntimeException("BackedFrugalSkiplist.append failed", e);
        }
    }


    // ---------------- FINDVISIBLE ----------------
    @Override
    public P findVisible(long timestamp) {
        try {
            String curKey = headKey;

            while (curKey != null) {
                NodeRecord cur = load(curKey);
                if (cur == null) break;

                // FOUND visible version
                if (cur.timestamp <= timestamp) {
                    return serializer.deSerialize(cur.payload);
                }

                // Try skip pointer (vRidgy) first
                if (cur.vRidgyKey != null) {
                    NodeRecord skip = load(cur.vRidgyKey);

                    // Only jump if skip timestamp >= t (do not jump too far)
                    if (skip != null && skip.timestamp >= timestamp) {
                        curKey = cur.vRidgyKey;
                        continue;
                    }
                }

                // Otherwise follow next pointer
                curKey = cur.nextKey;
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("BackedFrugalSkiplist.findVisible failed", e);
        }
    }
}
