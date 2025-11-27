import java.util.HashMap;
import java.util.Map;

public class InMemoryKVStore implements FlushableKVStore {

    private final Map<String, String> data = new HashMap<>();

    @Override
    public void put(String storeKey, String storeValue) {
        data.put(storeKey, storeValue);
    }

    @Override
    public String get(String storeKey) {
        return data.get(storeKey);
    }

    @Override
    public void flushDB() {
        // nothing to do for in-memory testing
    }
}
