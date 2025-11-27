// package your.package.name;  // <-- if VersionList.java has a package line, copy it here

public class ManualTest {

    public static void main(String[] args) {

        // -----------------------
        // TEST 1: VLinkedList
        // -----------------------
        System.out.println("=== Testing VLinkedList ===");

        VersionList<String> vList = new VLinkedList<>();

        vList.append("ten", 2);
        vList.append("five", 7);
        vList.append("ten", 8);

        System.out.println("vList.findVisible(1)   = " + vList.findVisible(1));    // expected: null
        System.out.println("vList.findVisible(2)   = " + vList.findVisible(2));    // expected: ten
        System.out.println("vList.findVisible(6)   = " + vList.findVisible(6));    // expected: ten
        System.out.println("vList.findVisible(7)   = " + vList.findVisible(7));    // expected: five
        System.out.println("vList.findVisible(8)   = " + vList.findVisible(8));    // expected: ten
        System.out.println("vList.findVisible(100) = " + vList.findVisible(100));  // expected: ten


        // -----------------------
        // TEST 2: BackedVLinkedList
        // -----------------------
        System.out.println("\n=== Testing BackedVLinkedList ===");

        // Our simple in-memory KVStore implementation
        KVStore kv = new InMemoryKVStore();

        // Simple serializer for String (no real conversion needed)
        Serializer<String> ser = new Serializer<String>() {
            @Override
            public String serialize(String t) {
                return t;
            }

            @Override
            public String deSerialize(String serializedT) {
                return serializedT;
            }
        };

        VersionList<String> bList = new BackedVLinkedList<>(kv, ser);

        bList.append("ten", 2);
        bList.append("five", 7);
        bList.append("ten", 8);

        System.out.println("bList.findVisible(1)   = " + bList.findVisible(1));    // expected: null
        System.out.println("bList.findVisible(2)   = " + bList.findVisible(2));    // expected: ten
        System.out.println("bList.findVisible(6)   = " + bList.findVisible(6));    // expected: ten
        System.out.println("bList.findVisible(7)   = " + bList.findVisible(7));    // expected: five
        System.out.println("bList.findVisible(8)   = " + bList.findVisible(8));    // expected: ten
        System.out.println("bList.findVisible(100) = " + bList.findVisible(100));  // expected: ten
    }
}
