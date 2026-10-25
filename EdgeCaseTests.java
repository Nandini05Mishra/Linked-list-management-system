/**
 * Self-contained edge-case tests (no JUnit needed).
 * Run:  java -cp out EdgeCaseTests
 */
public class EdgeCaseTests {
    private static int passed = 0, failed = 0;

    private static void eq(String name, Object actual, Object expected) {
        boolean ok = (actual == null) ? expected == null : actual.equals(expected);
        if (ok) passed++;
        else {
            failed++;
            System.out.println("  FAIL: " + name + "\n        expected: " + expected + "\n        actual:   " + actual);
        }
    }

    public static void main(String[] args) {
        System.out.println("Running edge-case tests...");
        System.out.println("-- Singly Linked List");
        testSingly();
        System.out.println("-- Doubly Linked List");
        testDoubly();
        System.out.println();
        System.out.println("Passed: " + passed + "   Failed: " + failed);
        if (failed > 0) System.exit(1);
    }

    private static SinglyLinkedList sOf(int... v) {
        SinglyLinkedList l = new SinglyLinkedList();
        for (int x : v) l.insertAtEnd(x);
        return l;
    }

    private static DoublyLinkedList dOf(int... v) {
        DoublyLinkedList l = new DoublyLinkedList();
        for (int x : v) l.insertAtEnd(x);
        return l;
    }

    private static void testSingly() {
        // Empty list
        SinglyLinkedList e = new SinglyLinkedList();
        eq("S empty display", e.display(), "NULL");
        eq("S empty count", e.count(), 0);
        eq("S empty delete begin", e.deleteFromBeginning(), false);
        eq("S empty delete end", e.deleteFromEnd(), false);
        eq("S empty delete value", e.deleteByValue(5), false);
        eq("S empty search", e.search(5), -1);
        eq("S empty max", e.findMax(), null);
        eq("S empty min", e.findMin(), null);
        eq("S empty middle", e.findMiddle(), null);
        eq("S empty insertBefore", e.insertBefore(1, 2), false);
        eq("S empty insertAfter", e.insertAfter(1, 2), false);
        e.reverse(); e.bubbleSort(); e.mergeSort();
        eq("S empty reverse/sort", e.display(), "NULL");
        eq("S empty removeDuplicates", e.removeDuplicates(), 0);
        eq("S insert pos 1 on empty", e.insertAtPosition(1, 7), true);
        eq("S insert pos 3 on 1-node (invalid)", e.insertAtPosition(3, 9), false);

        // One-node list
        SinglyLinkedList one = sOf(10);
        eq("S one-node middle", one.findMiddle(), 10);
        one.reverse();
        eq("S one-node reverse", one.display(), "10 -> NULL");
        one.bubbleSort(); one.mergeSort();
        eq("S one-node sort", one.display(), "10 -> NULL");
        eq("S one-node delete end", one.deleteFromEnd(), true);
        eq("S one-node after delete", one.display(), "NULL");

        // Positions
        SinglyLinkedList p = sOf(10, 20, 30);
        eq("S insert pos 1", p.insertAtPosition(1, 5), true);
        eq("S after pos 1", p.display(), "5 -> 10 -> 20 -> 30 -> NULL");
        eq("S insert middle", p.insertAtPosition(3, 15), true);
        eq("S after middle", p.display(), "5 -> 10 -> 15 -> 20 -> 30 -> NULL");
        eq("S insert last valid (count+1)", p.insertAtPosition(6, 99), true);
        eq("S after last valid", p.display(), "5 -> 10 -> 15 -> 20 -> 30 -> 99 -> NULL");
        eq("S insert pos 0", p.insertAtPosition(0, 1), false);
        eq("S insert pos -3", p.insertAtPosition(-3, 1), false);
        eq("S insert pos 50", p.insertAtPosition(50, 1), false);
        eq("S delete pos 1", p.deleteAtPosition(1), true);
        eq("S delete last pos", p.deleteAtPosition(5), true);
        eq("S after deletes", p.display(), "10 -> 15 -> 20 -> 30 -> NULL");
        eq("S delete pos 0", p.deleteAtPosition(0), false);
        eq("S delete pos out of range", p.deleteAtPosition(9), false);
        eq("S delete pos count+1", p.deleteAtPosition(5), false);

        // Before / after / by value
        SinglyLinkedList b = sOf(10, 20, 30);
        eq("S insertBefore head", b.insertBefore(10, 5), true);
        eq("S insertBefore middle", b.insertBefore(30, 25), true);
        eq("S insertAfter last", b.insertAfter(30, 40), true);
        eq("S insertAfter missing", b.insertAfter(777, 1), false);
        eq("S insertBefore missing", b.insertBefore(777, 1), false);
        eq("S before/after result", b.display(), "5 -> 10 -> 20 -> 25 -> 30 -> 40 -> NULL");
        eq("S delete value head", b.deleteByValue(5), true);
        eq("S delete value tail", b.deleteByValue(40), true);
        eq("S delete value missing", b.deleteByValue(12345), false);
        eq("S delete value result", b.display(), "10 -> 20 -> 25 -> 30 -> NULL");

        // Duplicates, zero and negatives
        SinglyLinkedList d = sOf(3, -1, 0, 3, -1, 0, 3, 5);
        eq("S first-match search", d.search(3), 1);
        eq("S negative search", d.search(-1), 2);
        eq("S zero search", d.search(0), 3);
        eq("S removeDuplicates count", d.removeDuplicates(), 4);
        eq("S removeDuplicates result", d.display(), "3 -> -1 -> 0 -> 5 -> NULL");
        eq("S max with negatives", sOf(-5, -2, -9).findMax(), -2);
        eq("S min with negatives", sOf(-5, -2, -9).findMin(), -9);
        eq("S update", d.update(0, 100), true);
        eq("S update missing", d.update(0, 1), false);

        // Middle rule
        eq("S middle odd", sOf(1, 2, 3, 4, 5).findMiddle(), 3);
        eq("S middle even (second middle)", sOf(10, 20, 30, 40).findMiddle(), 30);

        // Sorting / reverse (spec example)
        SinglyLinkedList s = sOf(40, 10, 30, 20, 50);
        s.bubbleSort();
        eq("S bubble sort", s.display(), "10 -> 20 -> 30 -> 40 -> 50 -> NULL");
        s.reverse();
        eq("S reverse", s.display(), "50 -> 40 -> 30 -> 20 -> 10 -> NULL");
        SinglyLinkedList m = sOf(5, -3, 0, 5, 2, -3, 9);
        m.mergeSort();
        eq("S merge sort", m.display(), "-3 -> -3 -> 0 -> 2 -> 5 -> 5 -> 9 -> NULL");
        eq("S count", m.count(), 7);
    }

    private static void testDoubly() {
        // Empty list
        DoublyLinkedList e = new DoublyLinkedList();
        eq("D empty forward", e.displayForward(), "NULL -> NULL");
        eq("D empty backward", e.displayBackward(), "NULL -> NULL");
        eq("D empty count", e.count(), 0);
        eq("D empty delete begin", e.deleteFromBeginning(), false);
        eq("D empty delete end", e.deleteFromEnd(), false);
        eq("D empty delete value", e.deleteByValue(1), false);
        eq("D empty search", e.search(1), -1);
        eq("D empty max/min/middle", e.findMax() == null && e.findMin() == null && e.findMiddle() == null, true);
        e.reverse(); e.bubbleSort(); e.mergeSort();
        eq("D empty reverse/sort", e.count(), 0);
        eq("D empty removeDuplicates", e.removeDuplicates(), 0);

        // One-node list: head == tail handling
        DoublyLinkedList one = dOf(10);
        eq("D one forward", one.displayForward(), "NULL <- 10 -> NULL");
        eq("D one backward", one.displayBackward(), "NULL <- 10 -> NULL");
        one.reverse();
        eq("D one reverse", one.displayForward(), "NULL <- 10 -> NULL");
        eq("D one delete end", one.deleteFromEnd(), true);
        eq("D one then empty fwd", one.displayForward(), "NULL -> NULL");
        one.insertAtEnd(1);
        eq("D reuse after empty (fwd)", one.displayForward(), "NULL <- 1 -> NULL");
        eq("D reuse after empty (bwd)", one.displayBackward(), "NULL <- 1 -> NULL");

        // Spec example: forward and backward
        DoublyLinkedList l = dOf(10, 20, 30, 40);
        eq("D forward", l.displayForward(), "NULL <- 10 <-> 20 <-> 30 <-> 40 -> NULL");
        eq("D backward", l.displayBackward(), "NULL <- 40 <-> 30 <-> 20 <-> 10 -> NULL");

        // Positions
        DoublyLinkedList p = dOf(10, 20, 30);
        eq("D insert pos 1", p.insertAtPosition(1, 5), true);
        eq("D insert middle", p.insertAtPosition(3, 15), true);
        eq("D insert last valid", p.insertAtPosition(6, 99), true);
        eq("D fwd after inserts", p.displayForward(), "NULL <- 5 <-> 10 <-> 15 <-> 20 <-> 30 <-> 99 -> NULL");
        eq("D bwd after inserts", p.displayBackward(), "NULL <- 99 <-> 30 <-> 20 <-> 15 <-> 10 <-> 5 -> NULL");
        eq("D insert pos 0", p.insertAtPosition(0, 1), false);
        eq("D insert pos 50", p.insertAtPosition(50, 1), false);
        eq("D delete pos 1", p.deleteAtPosition(1), true);
        eq("D delete last pos", p.deleteAtPosition(5), true);
        eq("D delete out of range", p.deleteAtPosition(9), false);
        eq("D fwd after deletes", p.displayForward(), "NULL <- 10 <-> 15 <-> 20 <-> 30 -> NULL");
        eq("D bwd after deletes", p.displayBackward(), "NULL <- 30 <-> 20 <-> 15 <-> 10 -> NULL");

        // Before / after / by value (both directions must stay consistent)
        DoublyLinkedList b = dOf(10, 20, 30);
        eq("D insertBefore head", b.insertBefore(10, 5), true);
        eq("D insertAfter tail", b.insertAfter(30, 40), true);
        eq("D insertBefore middle", b.insertBefore(20, 15), true);
        eq("D insertAfter missing", b.insertAfter(777, 1), false);
        eq("D b fwd", b.displayForward(), "NULL <- 5 <-> 10 <-> 15 <-> 20 <-> 30 <-> 40 -> NULL");
        eq("D b bwd", b.displayBackward(), "NULL <- 40 <-> 30 <-> 20 <-> 15 <-> 10 <-> 5 -> NULL");
        eq("D delete value head", b.deleteByValue(5), true);
        eq("D delete value tail", b.deleteByValue(40), true);
        eq("D delete value missing", b.deleteByValue(999), false);
        eq("D after delete fwd", b.displayForward(), "NULL <- 10 <-> 15 <-> 20 <-> 30 -> NULL");
        eq("D after delete bwd", b.displayBackward(), "NULL <- 30 <-> 20 <-> 15 <-> 10 -> NULL");

        // Duplicates, zero, negatives
        DoublyLinkedList d = dOf(3, -1, 0, 3, -1, 0, 3, 5);
        eq("D removeDuplicates count", d.removeDuplicates(), 4);
        eq("D dup fwd", d.displayForward(), "NULL <- 3 <-> -1 <-> 0 <-> 5 -> NULL");
        eq("D dup bwd", d.displayBackward(), "NULL <- 5 <-> 0 <-> -1 <-> 3 -> NULL");
        DoublyLinkedList tailDup = dOf(1, 2, 2, 2);
        tailDup.removeDuplicates();
        tailDup.insertAtEnd(9);   // tail must have been repaired
        eq("D dup at tail fwd", tailDup.displayForward(), "NULL <- 1 <-> 2 <-> 9 -> NULL");
        eq("D dup at tail bwd", tailDup.displayBackward(), "NULL <- 9 <-> 2 <-> 1 -> NULL");
        eq("D max/min negatives", dOf(-5, -2, -9).findMax() == -2 && dOf(-5, -2, -9).findMin() == -9, true);
        eq("D update", d.update(5, 50), true);
        eq("D update missing", d.update(5, 1), false);

        // Middle rule
        eq("D middle odd", dOf(1, 2, 3, 4, 5).findMiddle(), 3);
        eq("D middle even (second middle)", dOf(10, 20, 30, 40).findMiddle(), 30);

        // Reverse: head/tail swapped and both link directions valid
        DoublyLinkedList r = dOf(10, 20, 30, 40);
        r.reverse();
        eq("D reverse fwd", r.displayForward(), "NULL <- 40 <-> 30 <-> 20 <-> 10 -> NULL");
        eq("D reverse bwd", r.displayBackward(), "NULL <- 10 <-> 20 <-> 30 <-> 40 -> NULL");
        r.insertAtEnd(5);
        r.insertAtBeginning(50);
        eq("D reverse then insert fwd", r.displayForward(), "NULL <- 50 <-> 40 <-> 30 <-> 20 <-> 10 <-> 5 -> NULL");
        eq("D reverse then insert bwd", r.displayBackward(), "NULL <- 5 <-> 10 <-> 20 <-> 30 <-> 40 <-> 50 -> NULL");

        // Sorting: both directions must be valid afterwards
        DoublyLinkedList s = dOf(40, 10, 30, 20, 50);
        s.bubbleSort();
        eq("D bubble fwd", s.displayForward(), "NULL <- 10 <-> 20 <-> 30 <-> 40 <-> 50 -> NULL");
        eq("D bubble bwd", s.displayBackward(), "NULL <- 50 <-> 40 <-> 30 <-> 20 <-> 10 -> NULL");
        DoublyLinkedList m = dOf(5, -3, 0, 5, 2, -3, 9);
        m.mergeSort();
        eq("D merge fwd", m.displayForward(), "NULL <- -3 <-> -3 <-> 0 <-> 2 <-> 5 <-> 5 <-> 9 -> NULL");
        eq("D merge bwd", m.displayBackward(), "NULL <- 9 <-> 5 <-> 5 <-> 2 <-> 0 <-> -3 <-> -3 -> NULL");
        m.insertAtEnd(100);   // tail must be correct after merge sort
        eq("D merge then insertAtEnd bwd", m.displayBackward(), "NULL <- 100 <-> 9 <-> 5 <-> 5 <-> 2 <-> 0 <-> -3 <-> -3 -> NULL");
    }
}
