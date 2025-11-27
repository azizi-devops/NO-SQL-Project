# NO-SQL-Project
                *********************** Versioned Collection & Frugal Skiplist Project ************************

Winter Semester 2025/2026 — NoSQL Project

📌 1. Project Goal

The goal of this project is to implement a multi-versioned data structure that supports:

Appending values with a version (timestamp)

Querying the visible value at a given timestamp

Two storage modes:

In-memory (volatile)

Persistent (backed by a KVStore + JSON serialization)

Two data structures:

Simple linked list (VLinkedList)

Frugal Skiplist (optimized version list with skip pointers)

The project follows the task description provided by the professor.

📌 2. Implemented Classes

This project contains four main implementations of VersionList<P>.

✅ 2.1 VLinkedList<P> (In-memory simple version list)

A very simple version list:

Each inserted version becomes the head of a linked list.

Each node contains:

payload (value)

timestamp

next pointer to older version

Query findVisible(t) walks down the list until it finds the first timestamp ≤ t.

Complexity

Append: O(1)

FindVisible: O(n) worst-case

✅ 2.2 BackedVLinkedList<P> (Persistent version list)

Same behavior as VLinkedList, but nodes are stored in a KVStore.

Payload P is serialized to String using Serializer<P>.

NodeRecord is serialized to JSON using Jackson (ObjectMapper).

Stored in KVStore as:

key = timestamp as String
value = JSON representation of NodeRecord


Works exactly like the in-memory version but data survives between runs.

✅ 2.3 FrugalSkiplist<P> (In-memory optimized skiplist)

This improves search time by adding:

A level for each node (random)

A vRidgy skip pointer (points to an older node with higher level)

This helps skip over many nodes during findVisible().

Behavior

Insert uses Algorithm 1 from the project description

Query uses Algorithm 2

Complexity

Append: O(log n) average due to node levels

FindVisible: O(log n) average due to skip pointers

✅ 2.4 BackedFrugalSkiplist<P> (Persistent skiplist)

Same structure as FrugalSkiplist, but:

Each node is stored in KVStore as a JSON record

NodeRecord stores:

timestamp

level

payload (serialized)

nextKey

vRidgyKey

This gives a persistent skiplist versioned storage.

📌 3. Supporting Components
✔ Serializer<T>

Interface to convert values <T> into Strings and back.

We used a simple example serializer for Strings:

serialize("abc") → "abc"
deserialize("abc") → "abc"

✔ KVStore

Simple key–value store interface:

put(String key, String value)
get(String key)

✔ FlushableKVStore

Extends KVStore and adds:

flushDB()

✔ InMemoryKVStore

A custom implementation we created for testing:

Stores data in a HashMap

Implements FlushableKVStore

Allows BackedVLinkedList and BackedFrugalSkiplist to be tested without a real database

📌 4. Manual Testing (ManualTest.java)

We created a test class where we validated:

VLinkedList

BackedVLinkedList

FrugalSkiplist

BackedFrugalSkiplist

All tests insert values:

("ten", 2)
("five", 7)
("ten", 8)


And verify the result of:

findVisible(1)   → null
findVisible(2)   → ten
findVisible(6)   → ten
findVisible(7)   → five
findVisible(8)   → ten
findVisible(100) → ten


All implementations return the same correct values.

📌 5. Jackson Integration

We added three Jackson .jar dependencies:

jackson-core-2.15.0.jar
jackson-annotations-2.15.0.jar
jackson-databind-2.15.0.jar


These allow converting NodeRecord objects to/from JSON for persistence.

📌 6. Summary of What Was Done

✔ Implemented simple versioned list (in-memory & persistent)
✔ Implemented optimized Frugal Skiplist version list (in-memory & persistent)
✔ Implemented a KVStore test backend
✔ Integrated Jackson serialization
✔ Created ManualTest to verify correctness
✔ Ensured all four data structures return identical results
✔ Prepared project for the next architecture layers (MultiVersionMap, queries, etc.)