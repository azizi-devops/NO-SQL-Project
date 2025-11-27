import java.util.*;
import java.util.Map.Entry;

public class BackedSimpleMVM<K extends Comparable<? super K>, P>
        implements MultiVersionMap<K, P> {

    private final TreeMap<K, VersionList<P>> map;
    private final VersionListFactory<P> factory;
    private final KVStore store;
    private final Serializer<P> serializer;

    // Version counter starts at 1
    private long versionCounter = 1;

    public BackedSimpleMVM(VersionListFactory<P> factory,
                           KVStore store,
                           Serializer<P> serializer) {
        this.map = new TreeMap<>();
        this.factory = factory;
        this.store = store;
        this.serializer = serializer;
    }

    /**
     * Get or create a VersionList for the given key.
     */
    private VersionList<P> getOrCreateList(K key) {
        return map.computeIfAbsent(key, k -> factory.create(store, serializer));
    }

    /**
     * Appends a payload p for key k, assigning a new version number.
     */
    @Override
    public long append(K k, P p) {
        long assignedVersion = versionCounter++;
        VersionList<P> list = getOrCreateList(k);
        list.append(p, assignedVersion);
        return assignedVersion;
    }

    /**
     * Returns the visible version of key k at timestamp t.
     */
    @Override
    public Entry<K, P> get(K k, long t) {
        VersionList<P> list = map.get(k);
        if (list == null) {
            return null;
        }
        P visible = list.findVisible(t);
        if (visible == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(k, visible);
    }

    /**
     * Range snapshot between keys.
     */
    @Override
    public Iterator<Entry<K, P>> rangeSnapshot(K fromKey, boolean fromInclusive,
                                               K toKey, boolean toInclusive,
                                               long timestamp) {

        SortedMap<K, VersionList<P>> sub =
                map.subMap(fromKey, fromInclusive, toKey, toInclusive);

        List<Entry<K, P>> result = new ArrayList<>();

        for (K key : sub.keySet()) {
            VersionList<P> list = sub.get(key);
            if (list == null) continue;

            P visible = list.findVisible(timestamp);
            if (visible != null) {
                result.add(new AbstractMap.SimpleEntry<>(key, visible));
            }
        }

        return result.iterator();
    }

    /**
     * Full snapshot at timestamp t.
     */
    @Override
    public Iterator<Entry<K, P>> snapshot(long timestamp) {

        List<Entry<K, P>> result = new ArrayList<>();

        for (K key : map.keySet()) {
            VersionList<P> list = map.get(key);
            if (list == null) continue;

            P visible = list.findVisible(timestamp);
            if (visible != null) {
                result.add(new AbstractMap.SimpleEntry<>(key, visible));
            }
        }

        return result.iterator();
    }
}
