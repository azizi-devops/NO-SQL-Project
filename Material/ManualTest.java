// package your.package.name;  // <-- if VersionList.java has a package line, copy it here

public class ManualTest {

    public static void main(String[] args) {

        // -----------------------
        // TEST 1: VLinkedList
        // -----------------------
       /* System.out.println("=== Testing VLinkedList ===");

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
*/
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // -----------------------
        // TEST 2: BackedVLinkedList
        // -----------------------
/*        System.out.println("\n=== Testing BackedVLinkedList ===");

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

 */
// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        // -----------------------
        // TEST 3: FrugalSkiplist
        // -----------------------
/*        System.out.println("\n=== Testing FrugalSkiplist ===");

        VersionList<String> sList = new FrugalSkiplist<>();

        sList.append("ten", 2);
        sList.append("five", 7);
        sList.append("ten", 8);

        System.out.println("sList.findVisible(1)   = " + sList.findVisible(1));    // expected: null
        System.out.println("sList.findVisible(2)   = " + sList.findVisible(2));    // expected: ten
        System.out.println("sList.findVisible(6)   = " + sList.findVisible(6));    // expected: ten
        System.out.println("sList.findVisible(7)   = " + sList.findVisible(7));    // expected: five
        System.out.println("sList.findVisible(8)   = " + sList.findVisible(8));    // expected: ten
        System.out.println("sList.findVisible(100) = " + sList.findVisible(100));  // expected: ten

*/

// ///////////////////////////////////////////////////////////////////////////////////////////////////////




// -----------------------
        // TEST 4: BackedFrugalSkiplist
        // -----------------------
/*
        System.out.println("\n=== Testing BackedFrugalSkiplist ===");
        KVStore kv2 = new InMemoryKVStore();
        Serializer<String> ser2 = new Serializer<String>() {
            @Override
            public String serialize(String t) { return t; }
            @Override
            public String deSerialize(String s) { return s; }
        };
        VersionList<String> bfList = new BackedFrugalSkiplist<>(kv2, ser2);

        bfList.append("ten", 2);
        bfList.append("five", 7);
        bfList.append("ten", 8);

        System.out.println("bfList.findVisible(1)   = " + bfList.findVisible(1));
        System.out.println("bfList.findVisible(2)   = " + bfList.findVisible(2));
        System.out.println("bfList.findVisible(6)   = " + bfList.findVisible(6));
        System.out.println("bfList.findVisible(7)   = " + bfList.findVisible(7));
        System.out.println("bfList.findVisible(8)   = " + bfList.findVisible(8));
        System.out.println("bfList.findVisible(100) = " + bfList.findVisible(100));
*/
    }
}
