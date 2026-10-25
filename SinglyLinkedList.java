import java.util.Scanner;

/**
 * Singly Linked List built from scratch (no arrays / java.util collections).
 * Positions are 1-based. Logic methods do not print; the menu() method handles all I/O.
 */
public class SinglyLinkedList {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;

    // ------------------------------------------------------------------ Insert

    /** O(1) */
    public void insertAtBeginning(int value) {
        Node n = new Node(value);
        n.next = head;
        head = n;
    }

    /** O(n) - no tail pointer */
    public void insertAtEnd(int value) {
        Node n = new Node(value);
        if (head == null) {
            head = n;
            return;
        }
        Node temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = n;
    }

    /** Valid positions: 1 .. count()+1 */
    public boolean insertAtPosition(int pos, int value) {
        if (pos < 1) return false;
        if (pos == 1) {
            insertAtBeginning(value);
            return true;
        }
        Node prev = head;
        for (int i = 1; i < pos - 1 && prev != null; i++) prev = prev.next;
        if (prev == null) return false;
        Node n = new Node(value);
        n.next = prev.next;
        prev.next = n;
        return true;
    }

    /** Inserts after the FIRST node holding target. */
    public boolean insertAfter(int target, int value) {
        Node found = head;
        while (found != null && found.data != target) found = found.next;
        if (found == null) return false;
        Node n = new Node(value);
        n.next = found.next;
        found.next = n;
        return true;
    }

    /** Inserts before the FIRST node holding target. */
    public boolean insertBefore(int target, int value) {
        if (head == null) return false;
        if (head.data == target) {
            insertAtBeginning(value);
            return true;
        }
        Node prev = head;
        while (prev.next != null && prev.next.data != target) prev = prev.next;
        if (prev.next == null) return false;
        Node n = new Node(value);
        n.next = prev.next;
        prev.next = n;
        return true;
    }

    // ------------------------------------------------------------------ Delete

    /** O(1) */
    public boolean deleteFromBeginning() {
        if (head == null) return false;
        head = head.next;
        return true;
    }

    /** O(n) - must find the second-last node */
    public boolean deleteFromEnd() {
        if (head == null) return false;
        if (head.next == null) {
            head = null;
            return true;
        }
        Node temp = head;
        while (temp.next.next != null) temp = temp.next;
        temp.next = null;
        return true;
    }

    public boolean deleteAtPosition(int pos) {
        if (head == null || pos < 1) return false;
        if (pos == 1) return deleteFromBeginning();
        Node prev = head;
        for (int i = 1; i < pos - 1 && prev != null; i++) prev = prev.next;
        if (prev == null || prev.next == null) return false;
        prev.next = prev.next.next;
        return true;
    }

    /** Deletes the FIRST node holding value. */
    public boolean deleteByValue(int value) {
        if (head == null) return false;
        if (head.data == value) {
            head = head.next;
            return true;
        }
        Node prev = head;
        while (prev.next != null && prev.next.data != value) prev = prev.next;
        if (prev.next == null) return false;
        prev.next = prev.next.next;
        return true;
    }

    // ------------------------------------------------- Search / Update / Info

    /** Returns 1-based position of the first match, or -1. */
    public int search(int value) {
        Node temp = head;
        int pos = 1;
        while (temp != null) {
            if (temp.data == value) return pos;
            temp = temp.next;
            pos++;
        }
        return -1;
    }

    /** Replaces the first node holding oldValue. */
    public boolean update(int oldValue, int newValue) {
        Node temp = head;
        while (temp != null && temp.data != oldValue) temp = temp.next;
        if (temp == null) return false;
        temp.data = newValue;
        return true;
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        Node temp = head;
        while (temp != null) {
            sb.append(temp.data).append(" -> ");
            temp = temp.next;
        }
        return sb.append("NULL").toString();
    }

    public int count() {
        int c = 0;
        for (Node t = head; t != null; t = t.next) c++;
        return c;
    }

    public boolean isEmpty() {
        return head == null;
    }

    /** Returns null when the list is empty. */
    public Integer findMax() {
        if (head == null) return null;
        int max = head.data;
        for (Node t = head.next; t != null; t = t.next) if (t.data > max) max = t.data;
        return max;
    }

    /** Returns null when the list is empty. */
    public Integer findMin() {
        if (head == null) return null;
        int min = head.data;
        for (Node t = head.next; t != null; t = t.next) if (t.data < min) min = t.data;
        return min;
    }

    /**
     * Slow/fast pointers. Rule for even length: returns the SECOND of the two middle nodes
     * (e.g. 10,20,30,40 -> 30). Returns null when the list is empty.
     */
    public Integer findMiddle() {
        if (head == null) return null;
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    // -------------------------------------------------------- Reverse / Sort

    /** O(n) time, O(1) extra space: previous / current / next. */
    public void reverse() {
        Node prev = null, curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        head = prev;
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

    /** Advanced version: Merge Sort by relinking nodes. O(n log n). */
    public void mergeSort() {
        head = mergeSortRec(head);
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
        Node tail = dummy;
        while (a != null && b != null) {
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    /** Keeps the first occurrence of each value. O(n^2). Returns how many nodes were removed. */
    public int removeDuplicates() {
        int removed = 0;
        for (Node curr = head; curr != null; curr = curr.next) {
            Node runner = curr;
            while (runner.next != null) {
                if (runner.next.data == curr.data) {
                    runner.next = runner.next.next;
                    removed++;
                } else {
                    runner = runner.next;
                }
            }
        }
        return removed;
    }

    // ------------------------------------------------------------------- Menu

    private void printMenu() {
        System.out.println();
        System.out.println("********** Operation on Singly Linked List **********");
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
        System.out.println("12. Display");
        System.out.println("13. Count Nodes");
        System.out.println("14. Find Maximum");
        System.out.println("15. Find Minimum");
        System.out.println("16. Find Middle Element");
        System.out.println("17. Reverse");
        System.out.println("18. Sort");
        System.out.println("19. Remove Duplicates");
        System.out.println("20. Exit");
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
                    System.out.println(isEmpty() ? "List is empty." : display());
                    break;
                case 13:
                    System.out.println("Number of nodes: " + count());
                    break;
                case 14: {
                    Integer m = findMax();
                    System.out.println(m == null ? "List is empty." : "Maximum value: " + m);
                    break;
                }
                case 15: {
                    Integer m = findMin();
                    System.out.println(m == null ? "List is empty." : "Minimum value: " + m);
                    break;
                }
                case 16: {
                    Integer m = findMiddle();
                    System.out.println(m == null ? "List is empty." : "Middle element: " + m);
                    break;
                }
                case 17:
                    reverse();
                    System.out.println("List reversed.");
                    break;
                case 18: {
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
                case 19:
                    System.out.println("Duplicates removed: " + removeDuplicates());
                    break;
                case 20:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-20.");
            }
        } while (choice != 20);
    }
}
