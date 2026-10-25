# Linked List Management System

## Objective
A menu-driven console application for practising linked-list data structures. It implements a **Singly Linked List** and a **Doubly Linked List** from scratch, with every operation written as a separate method, plus a side-by-side comparison of the two (theory + measured timings).

Built as a 2nd-year DSA mini project.

## Language Used: Java
Pure Java (JDK 8+). **No arrays, `ArrayList` or `LinkedList` are used for storage**. Nodes and links are implemented manually.

## Features
- Menu-driven console UI with input validation (non-numeric input never crashes the program)
- Complete Singly Linked List (19 operations) and Doubly Linked List (20 operations)
- Both **Bubble Sort** (beginner, O(n²)) and **Merge Sort** (advanced, O(n log n)) for both lists
- Lists keep their data while you move between menus
- Singly vs Doubly comparison: complexity table + live benchmark
- Edge-case test runner with 121 checks (empty list, one node, invalid positions, duplicates, negatives, ...)

## Singly Linked List
Each node stores `data` and `next`. Only a `head` reference is kept, so end operations are O(n).

## Doubly Linked List
Each node stores `data`, `prev` and `next`. Both `head` and `tail` are kept, so insert/delete at the end are O(1) and the list can be displayed backward.

## Operations

| # | Singly | Doubly |
|---|--------|--------|
| 1-5 | Insert at Beginning / End / Position / After a Node / Before a Node | same |
| 6-9 | Delete from Beginning / End / Position / by Value | same |
| 10-11 | Search, Update | same |
| 12-13 | Display | Display Forward, Display Backward |
| next | Count, Max, Min, Middle, Reverse, Sort, Remove Duplicates | same |

Design rules used:
- Positions are **1-based**. Insert accepts `1 .. count+1`; delete accepts `1 .. count`.
- "After / Before / Delete by Value / Update / Search" act on the **first** matching node.
- **Middle element:** slow/fast pointers; for an even-length list the **second** middle is returned (`10,20,30,40` -> `30`).
- **Remove Duplicates** keeps the first occurrence of each value.

## How to Run

```bash
# from the project root
mkdir out
javac -d out src/*.java

# run the application
java -cp out Main

# run the edge-case tests
java -cp out EdgeCaseTests
```

Regenerate the sample output:

```bash
java -cp out Main --echo < sample-output/sample-input.txt > sample-output/output.txt
```

## Sample Output
Full run: [`sample-output/output.txt`](sample-output/output.txt)

```
Singly:   40 -> 10 -> 30 -> 20 -> 50 -> NULL
Sorted:   10 -> 20 -> 30 -> 40 -> 50 -> NULL
Reversed: 50 -> 40 -> 30 -> 20 -> 10 -> NULL

Doubly forward:  NULL <- 10 <-> 20 <-> 30 <-> 40 -> NULL
Doubly backward: NULL <- 40 <-> 30 <-> 20 <-> 10 -> NULL
```

## Time Complexity

| Operation | Singly | Doubly |
|-----------|--------|--------|
| Insert at Beginning | O(1) | O(1) |
| Insert at End | O(n) (no tail) | O(1) (tail) |
| Insert at Position / After / Before | O(n) | O(n) |
| Delete from Beginning | O(1) | O(1) |
| Delete from End | O(n) | O(1) |
| Delete at Position / by Value | O(n) | O(n) |
| Search / Display / Count | O(n) | O(n) |
| Find Max / Min / Middle | O(n) | O(n) |
| Reverse | O(n), O(1) space | O(n), O(1) space |
| Bubble Sort | O(n²) | O(n²) |
| Merge Sort | O(n log n) | O(n log n) |
| Remove Duplicates (simple) | O(n²) | O(n²) |

## Project Structure
```
Linked-List-Management-System/
|-- README.md
|-- src/
|   |-- Main.java              # main menu
|   |-- SinglyLinkedList.java  # list + its menu
|   |-- DoublyLinkedList.java  # list + its menu
|   |-- ListComparison.java    # option 3: comparison + benchmark
|   |-- InputUtil.java         # safe integer input
|   `-- EdgeCaseTests.java     # edge-case test runner
|-- sample-output/
|   |-- output.txt
|   `-- sample-input.txt
|-- docs/
|   `-- project-report.md
`-- .gitignore
```

## Author
Nandini
GitHub: [nandini05mishra](https://github.com/nandini05mishra)
