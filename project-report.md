# Project Report - Linked List Management System

## 1. Design
- `SinglyLinkedList` and `DoublyLinkedList` each contain a private static `Node` class. Logic methods return values (`boolean`, `int`, `Integer`, `String`) and never print; only `menu()` does I/O. This keeps the data-structure code testable (see `EdgeCaseTests`).
- The doubly list uses private helpers (`linkAfter`, `linkBefore`, `unlink`, `nodeAt`, `findFirst`) so every pointer fix-up (neighbours, `head`, `tail`) lives in exactly one place.

## 2. Key algorithms
- **Reverse (singly):** walk with `prev`, `curr`, `next`; point each node back at `prev`. O(n), O(1) space.
- **Reverse (doubly):** swap `prev` and `next` in every node, then swap `head` and `tail`.
- **Middle:** slow moves 1 step, fast moves 2; when fast reaches the end, slow is at the middle (second middle for even length).
- **Bubble Sort:** swaps node data; each pass shrinks the unsorted region and stops early if no swap happens.
- **Merge Sort:** split with slow/fast, sort halves recursively, merge by relinking nodes (no data copying). For the doubly list the `prev` links and `tail` are rebuilt in one pass afterwards.
- **Remove Duplicates:** for each node, a runner removes later nodes with the same value. O(n²), O(1) space.

## 3. Edge cases covered by tests
Empty list; one-node list; insert/delete at position 1; last valid insert position (count+1); invalid positions (0, negative, too large); delete missing value; duplicates (including at the tail); zero and negative values; sort/reverse on empty and one-node lists; `head`/`tail` correctness after every operation; backward traversal checked after each doubly-list mutation.

## 4. Singly vs Doubly - summary
| | Singly | Doubly |
|---|---|---|
| Memory per node | 1 reference | 2 references |
| Backward traversal | No | Yes |
| Insert/Delete at end | O(n) | O(1) |
| Delete a node you already hold | O(n) | O(1) |
| Risk of pointer bugs | lower | higher (`prev`, `next`, `head`, `tail`) |

## 5. Java vs C++ (viva)
Java uses object references instead of raw pointers and has a garbage collector, so there is no `delete` - unlinking a node (no reference left) is enough for it to be reclaimed. `Scanner` / `System.out` replace `cin` / `cout`.
