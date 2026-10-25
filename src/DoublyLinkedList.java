import java.util.Scanner;

/**
 * Doubly Linked List built from scratch (no arrays / java.util collections).
 * Keeps both head and tail references. Positions are 1-based.
 * Logic methods do not print; the menu() method handles all I/O.
 */
public class DoublyLinkedList {

    private static class Node {
        int data;
        Node prev, next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head, tail;

    // ---------------------------------------------------------------- Helpers

    /** Node at 1-based position, or null if out of range. */
    private Node nodeAt(int pos) {
        if (pos < 1) return null;
        Node t = head;
        for (int i = 1; i < pos && t != null; i++) t = t.next;
        return t;
    }

    private Node findFirst(int value) {
        Node t = head;
        while (t != null && t.data != value) t = t.next;
        return t;
    }

    private void linkAfter(Node ref, int value) {
        Node n = new Node(value);
        n.prev = ref;
        n.next = ref.next;
        if (ref.next != null) ref.next.prev = n;
        else tail = n;
        ref.next = n;
    }

    private void linkBefore(Node ref, int value) {
        Node n = new Node(value);
        n.next = ref;
        n.prev = ref.prev;
        if (ref.prev != null) ref.prev.next = n;
        else head = n;
        ref.prev = n;
    }

    /** Detaches a node and repairs neighbours, head and tail. */
    private void unlink(Node n) {
        if (n.prev != null) n.prev.next = n.next;
        else head = n.next;
        if (n.next != null) n.next.prev = n.prev;
        else tail = n.prev;
        n.prev = null;
        n.next = null;
    }

    // ----------------------------------------------------------------- Insert

    /** O(1) */
    public void insertAtBeginning(int value) {
        Node n = new Node(value);
        n.next = head;
        if (head != null) head.prev = n;
        else tail = n;
        head = n;
    }

    /** O(1) - thanks to the tail reference */
    public void insertAtEnd(int value) {
        Node n = new Node(value);
        n.prev = tail;
        if (tail != null) tail.next = n;
        else head = n;
        tail = n;
    }

    /** Valid positions: 1 .. count()+1 */
    public boolean insertAtPosition(int pos, int value) {
        if (pos < 1) return false;
        if (pos == 1) {
            insertAtBeginning(value);
            return true;
        }
        Node before = nodeAt(pos - 1);
        if (before == null) return false;
        linkAfter(before, value);
        return true;
    }

    public boolean insertAfter(int target, int value) {
        Node f = findFirst(target);
        if (f == null) return false;
        linkAfter(f, value);
        return true;
    }

    public boolean insertBefore(int target, int value) {
        Node f = findFirst(target);
        if (f == null) return false;
        linkBefore(f, value);
        return true;
    }

    // ----------------------------------------------------------------- Delete

    public boolean deleteFromBeginning() {
        if (head == null) return false;
        unlink(head);
        return true;
    }

    /** O(1) - thanks to the tail reference */
    public boolean deleteFromEnd() {
        if (tail == null) return false;
        unlink(tail);
        return true;
    }

    public boolean deleteAtPosition(int pos) {
        Node n = nodeAt(pos);
        if (n == null) return false;
        unlink(n);
        return true;
    }

    public boolean deleteByValue(int value) {
        Node n = findFirst(value);
        if (n == null) return false;
        unlink(n);
        return true;
    }

    // ------------------------------------------------- Search / Update / Info

    public int search(int value) {
        int pos = 1;
        for (Node t = head; t != null; t = t.next, pos++) if (t.data == value) return pos;
        return -1;
    }

    public boolean update(int oldValue, int newValue) {
        Node n = findFirst(oldValue);
        if (n == null) return false;
        n.data = newValue;
        return true;
    }

    /** NULL <- 10 <-> 20 <-> 30 -> NULL */
    public String displayForward() {
        StringBuilder sb = new StringBuilder("NULL");
        for (Node t = head; t != null; t = t.next) {
            sb.append(t == head ? " <- " : " <-> ").append(t.data);
        }
        return sb.append(" -> NULL").toString();
    }

    /** Starts at the tail and follows prev links. */
    public String displayBackward() {
        StringBuilder sb = new StringBuilder("NULL");
        for (Node t = tail; t != null; t = t.prev) {
            sb.append(t == tail ? " <- " : " <-> ").append(t.data);
        }
        return sb.append(" -> NULL").toString();
    }

    public int count() {
        int c = 0;
        for (Node t = head; t != null; t = t.next) c++;
        return c;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Integer findMax() {
        if (head == null) return null;
        int max = head.data;
        for (Node t = head.next; t != null; t = t.next) if (t.data > max) max = t.data;
        return max;
    }

    public Integer findMin() {
        if (head == null) return null;
        int min = head.data;
        for (Node t = head.next; t != null; t = t.next) if (t.data < min) min = t.data;
        return min;
    }

    /** Slow/fast references. Even length -> SECOND of the two middle nodes. */
    public Integer findMiddle() {
        if (head == null) return null;
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    // --------------------------------------------------------- Reverse / Sort

    /** Swap prev/next in every node, then swap head and tail. O(n). */
    public void reverse() {
        Node curr = head;
        while (curr != null) {
            Node nxt = curr.next;
            curr.next = curr.prev;
            curr.prev = nxt;
            curr = nxt;
        }
        Node t = head;
        head = tail;
        tail = t;
    }

    /** Beginner version: Bubble Sort by swapping data. O(n^2). */
    public void bubbleSort() {
        if (head == null || head.next == null) return;
        Node end = null;
        boolean swapped;
        do {
            swapped = false;
            Node curr = head;
            while (curr.next != end) {
                if (curr.data > curr.next.data) {
                    int t = curr.data;
                    curr.data = curr.next.data;
                    curr.next.data = t;
                    swapped = true;
                }
                curr = curr.next;
            }
            end = curr;
        } while (swapped);
    }

    /** Advanced version: Merge Sort. Sorts using next links, then repairs prev links and tail. */
    public void mergeSort() {
        head = mergeSortRec(head);
        Node prev = null, curr = head;
        while (curr != null) {
            curr.prev = prev;
            prev = curr;
            curr = curr.next;
        }
        tail = prev;
    }

    private Node mergeSortRec(Node h) {
        if (h == null || h.next == null) return h;
        Node slow = h, fast = h.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = slow.next;
        slow.next = null;
        return merge(mergeSortRec(h), mergeSortRec(second));
    }

    private Node merge(Node a, Node b) {
        Node dummy = new Node(0);
        Node t = dummy;
        while (a != null && b != null) {
            if (a.data <= b.data) {
                t.next = a;
                a = a.next;
            } else {
                t.next = b;
                b = b.next;
            }
            t = t.next;
        }
        t.next = (a != null) ? a : b;
        return dummy.next;
    }

    /** Keeps the first occurrence of each value. O(n^2). Returns how many nodes were removed. */
    public int removeDuplicates() {
        int removed = 0;
        for (Node curr = head; curr != null; curr = curr.next) {
            Node runner = curr.next;
            while (runner != null) {
                Node nxt = runner.next;
                if (runner.data == curr.data) {
                    unlink(runner);
                    removed++;
                }
                runner = nxt;
            }
        }
        return removed;
    }

    // ------------------------------------------------------------------- Menu

    private void printMenu() {
        System.out.println();
        System.out.println("********** Operation on Doubly Linked List **********");
        System.out.println(" 1. Insert at Beginning");
        System.out.println(" 2. Insert at End");
        System.out.println(" 3. Insert at Position");
        System.out.println(" 4. Insert After a Node");
        System.out.println(" 5. Insert Before a Node");
        System.out.println(" 6. Delete from Beginning");
        System.out.println(" 7. Delete from End");
        System.out.println(" 8. Delete from Position");
        System.out.println(" 9. Delete by Value");
        System.out.println("10. Search");
        System.out.println("11. Update");
        System.out.println("12. Display Forward");
        System.out.println("13. Display Backward");
        System.out.println("14. Count Nodes");
        System.out.println("15. Find Maximum");
        System.out.println("16. Find Minimum");
        System.out.println("17. Find Middle Element");
        System.out.println("18. Reverse");
        System.out.println("19. Sort");
        System.out.println("20. Remove Duplicates");
        System.out.println("21. Exit");
    }

    public void menu(Scanner sc) {
        int choice;
        do {
            printMenu();
            choice = InputUtil.readInt(sc, "Enter the choice:- ");
            switch (choice) {
                case 1:
                    insertAtBeginning(InputUtil.readInt(sc, "Enter value: "));
                    System.out.println("Inserted at beginning.");
                    break;
                case 2:
                    insertAtEnd(InputUtil.readInt(sc, "Enter value: "));
                    System.out.println("Inserted at end.");
                    break;
                case 3: {
                    int pos = InputUtil.readInt(sc, "Enter position (1-based): ");
                    int val = InputUtil.readInt(sc, "Enter value: ");
                    if (insertAtPosition(pos, val)) System.out.println("Inserted " + val + " at position " + pos + ".");
                    else System.out.println("Invalid position. Valid range is 1 to " + (count() + 1) + ".");
                    break;
                }
                case 4: {
                    int target = InputUtil.readInt(sc, "Insert after which value? ");
                    int val = InputUtil.readInt(sc, "Enter new value: ");
                    if (insertAfter(target, val)) System.out.println("Inserted " + val + " after " + target + ".");
                    else System.out.println("Value " + target + " not found.");
                    break;
                }
                case 5: {
                    int target = InputUtil.readInt(sc, "Insert before which value? ");
                    int val = InputUtil.readInt(sc, "Enter new value: ");
                    if (insertBefore(target, val)) System.out.println("Inserted " + val + " before " + target + ".");
                    else System.out.println("Value " + target + " not found.");
                    break;
                }
                case 6:
                    System.out.println(deleteFromBeginning() ? "Deleted first node." : "List is empty. Nothing to delete.");
                    break;
                case 7:
                    System.out.println(deleteFromEnd() ? "Deleted last node." : "List is empty. Nothing to delete.");
                    break;
                case 8: {
                    int pos = InputUtil.readInt(sc, "Enter position (1-based): ");
                    if (deleteAtPosition(pos)) System.out.println("Deleted node at position " + pos + ".");
                    else System.out.println("Invalid position or empty list. Current node count: " + count() + ".");
                    break;
                }
                case 9: {
                    int val = InputUtil.readInt(sc, "Enter value to delete: ");
                    System.out.println(deleteByValue(val) ? "Deleted first node with value " + val + "." : "Value " + val + " not found.");
                    break;
                }
                case 10: {
                    int val = InputUtil.readInt(sc, "Enter value to search: ");
                    int pos = search(val);
                    System.out.println(pos == -1 ? "Value " + val + " not found." : "Value " + val + " found at position " + pos + ".");
                    break;
                }
                case 11: {
                    int oldV = InputUtil.readInt(sc, "Enter value to replace: ");
                    int newV = InputUtil.readInt(sc, "Enter new value: ");
                    System.out.println(update(oldV, newV) ? "Updated " + oldV + " to " + newV + "." : "Value " + oldV + " not found.");
                    break;
                }
                case 12:
                    System.out.println(isEmpty() ? "List is empty." : "Forward:  " + displayForward());
                    break;
                case 13:
                    System.out.println(isEmpty() ? "List is empty." : "Backward: " + displayBackward());
                    break;
                case 14:
                    System.out.println("Number of nodes: " + count());
                    break;
                case 15: {
                    Integer m = findMax();
                    System.out.println(m == null ? "List is empty." : "Maximum value: " + m);
                    break;
                }
                case 16: {
                    Integer m = findMin();
                    System.out.println(m == null ? "List is empty." : "Minimum value: " + m);
                    break;
                }
                case 17: {
                    Integer m = findMiddle();
                    System.out.println(m == null ? "List is empty." : "Middle element: " + m);
                    break;
                }
                case 18:
                    reverse();
                    System.out.println("List reversed (prev/next links swapped, head/tail updated).");
                    break;
                case 19: {
                    System.out.println("1. Bubble Sort  O(n^2)");
                    System.out.println("2. Merge Sort   O(n log n)");
                    int s = InputUtil.readInt(sc, "Choose sorting method: ");
                    if (s == 1) {
                        bubbleSort();
                        System.out.println("Sorted with Bubble Sort.");
                    } else if (s == 2) {
                        mergeSort();
                        System.out.println("Sorted with Merge Sort.");
                    } else System.out.println("Invalid sorting method.");
                    break;
                }
                case 20:
                    System.out.println("Duplicates removed: " + removeDuplicates());
                    break;
                case 21:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-21.");
            }
        } while (choice != 21);
    }
}
