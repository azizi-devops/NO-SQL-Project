// package ...;  // <-- only if VersionList.java has one

public class VLinkedList<P> implements VersionList<P> {

    /**
     * One element (node) in the linked list.
     * It stores:
     *  - the payload p
     *  - its timestamp
     *  - a reference to the next (older) node
     */
    private static class Node<P> {
        P payload;
        long timestamp;
        Node<P> next;

        Node(P payload, long timestamp, Node<P> next) {
            this.payload = payload;
            this.timestamp = timestamp;
            this.next = next;
        }
    }

    // Head of the list = newest version
    private Node<P> head;

    public VLinkedList() {
        this.head = null;
    }

    /**
     * Append is "prepend" in an append-only list:
     * we create a new node and put it in front,
     * pointing to the previous head (older versions).
     */
    @Override
    public void append(P p, long timestamp) {
        head = new Node<>(p, timestamp, head);
    }

    /**
     * We need the newest payload with timestamp <= given timestamp.
     * Because head is newest, we walk from head to older nodes
     * and return the first node whose timestamp is <= requested time.
     */
    @Override
    public P findVisible(long timestamp) {
        Node<P> current = head;

        while (current != null) {
            if (current.timestamp <= timestamp) {
                return current.payload;
            }
            current = current.next; // go to older version
        }

        // No version existed at or before the given time
        return null;
    }
}
