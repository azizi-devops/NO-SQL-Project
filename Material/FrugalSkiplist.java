// package ...;

public class FrugalSkiplist<P> implements VersionList<P> {
    private static class Node<P> {
        P payload;
        long timestamp;
        Node<P> next;     // next (older) version
        Node<P> vRidgy;   // skip pointer
        int level;        // node level

        Node(P payload, long timestamp, int level, Node<P> next, Node<P> vRidgy) {
            this.payload = payload;
            this.timestamp = timestamp;
            this.level = level;
            this.next = next;
            this.vRidgy = vRidgy;
        }
    }

    private Node<P> head;                    // newest version
    private final java.util.Random rnd = new java.util.Random();

    public FrugalSkiplist() {
        this.head = null;
    }

    private int randomLevel() {
        int lvl = 0;
        // simple geometric distribution, p = 0.5
        while (rnd.nextBoolean()) {
            lvl++;
        }
        return lvl;
    }
    @Override
    public void append(P p, long timestamp) {
        int lvl = randomLevel();

        Node<P> oldHead = head;
        Node<P> newNode = new Node<>(p, timestamp, lvl, oldHead, null);

        // Find vRidgy target
        Node<P> cursor = oldHead;
        while (cursor != null && cursor.level <= lvl) {
            cursor = cursor.next;
        }

        newNode.vRidgy = cursor;
        head = newNode;
    }

    @Override
    public P findVisible(long timestamp) {
        Node<P> cur = head;

        while (cur != null && cur.timestamp > timestamp) {
            // try to jump with vRidgy if it doesn't skip too far
            if (cur.vRidgy != null && cur.vRidgy.timestamp >= timestamp) {
                cur = cur.vRidgy;
            } else {
                cur = cur.next;
            }
        }

        return (cur == null) ? null : cur.payload;
    }


}
