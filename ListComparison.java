import java.util.Random;

/** Option 3 of the main menu: theory table + measured timings for Singly vs Doubly lists. */
public class ListComparison {

    private static final int N = 20000;      // operations for insert / delete timings
    private static final int SORT_N = 3000;  // elements for sort timings

    public static void run() {
        System.out.println();
        System.out.println("=========== SINGLY vs DOUBLY LINKED LIST ===========");
        System.out.println();
        System.out.printf("%-26s %-22s %-22s%n", "Feature", "Singly", "Doubly");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-26s %-22s %-22s%n", "Pointers per node", "1 (next)", "2 (prev, next)");
        System.out.printf("%-26s %-22s %-22s%n", "Extra memory per node", "less", "more (one extra ref)");
        System.out.printf("%-26s %-22s %-22s%n", "Traversal", "forward only", "forward + backward");
        System.out.printf("%-26s %-22s %-22s%n", "Insert at beginning", "O(1)", "O(1)");
        System.out.printf("%-26s %-22s %-22s%n", "Insert at end", "O(n) (no tail)", "O(1) (with tail)");
        System.out.printf("%-26s %-22s %-22s%n", "Delete from beginning", "O(1)", "O(1)");
        System.out.printf("%-26s %-22s %-22s%n", "Delete from end", "O(n)", "O(1) (with tail)");
        System.out.printf("%-26s %-22s %-22s%n", "Delete a known node", "O(n) need previous", "O(1) has prev link");
        System.out.printf("%-26s %-22s %-22s%n", "Search / Count / Min/Max", "O(n)", "O(n)");
        System.out.printf("%-26s %-22s %-22s%n", "Reverse", "O(n), 3 pointers", "O(n), swap prev/next");
        System.out.printf("%-26s %-22s %-22s%n", "Link bugs to watch for", "next only", "prev AND next, tail");
        System.out.println();

        System.out.println("Measured on this machine (N = " + N + " operations, sort size = " + SORT_N + "):");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-34s %12s %12s%n", "Operation", "Singly (ms)", "Doubly (ms)");

        // Insert at beginning
        SinglyLinkedList s = new SinglyLinkedList();
        DoublyLinkedList d = new DoublyLinkedList();
        long t0 = System.nanoTime();
        for (int i = 0; i < N; i++) s.insertAtBeginning(i);
        long sIns = System.nanoTime() - t0;
        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) d.insertAtBeginning(i);
        long dIns = System.nanoTime() - t0;
        row("Insert at beginning x N", sIns, dIns);

        // Insert at end
        s = new SinglyLinkedList();
        d = new DoublyLinkedList();
        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) s.insertAtEnd(i);
        long sEnd = System.nanoTime() - t0;
        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) d.insertAtEnd(i);
        long dEnd = System.nanoTime() - t0;
        row("Insert at end x N", sEnd, dEnd);

        // Search worst case
        t0 = System.nanoTime();
        for (int i = 0; i < 500; i++) s.search(-1);
        long sSearch = System.nanoTime() - t0;
        t0 = System.nanoTime();
        for (int i = 0; i < 500; i++) d.search(-1);
        long dSearch = System.nanoTime() - t0;
        row("Search (not found) x 500", sSearch, dSearch);

        // Reverse
        t0 = System.nanoTime();
        s.reverse();
        long sRev = System.nanoTime() - t0;
        t0 = System.nanoTime();
        d.reverse();
        long dRev = System.nanoTime() - t0;
        row("Reverse once", sRev, dRev);

        // Delete from end
        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) s.deleteFromEnd();
        long sDel = System.nanoTime() - t0;
        t0 = System.nanoTime();
        for (int i = 0; i < N; i++) d.deleteFromEnd();
        long dDel = System.nanoTime() - t0;
        row("Delete from end x N", sDel, dDel);

        // Sorting: bubble vs merge
        SinglyLinkedList sb = new SinglyLinkedList(), sm = new SinglyLinkedList();
        DoublyLinkedList db = new DoublyLinkedList(), dm = new DoublyLinkedList();
        Random r = new Random(42);
        for (int i = 0; i < SORT_N; i++) {
            int v = r.nextInt(100000);
            sb.insertAtBeginning(v);
            sm.insertAtBeginning(v);
            db.insertAtBeginning(v);
            dm.insertAtBeginning(v);
        }
        t0 = System.nanoTime();
        sb.bubbleSort();
        long sBub = System.nanoTime() - t0;
        t0 = System.nanoTime();
        db.bubbleSort();
        long dBub = System.nanoTime() - t0;
        row("Bubble Sort", sBub, dBub);
        t0 = System.nanoTime();
        sm.mergeSort();
        long sMer = System.nanoTime() - t0;
        t0 = System.nanoTime();
        dm.mergeSort();
        long dMer = System.nanoTime() - t0;
        row("Merge Sort", sMer, dMer);

        System.out.println();
        System.out.println("Takeaway: the doubly list costs more memory and more pointer updates,");
        System.out.println("but gives O(1) end operations, backward traversal and O(1) deletion of a known node.");
        System.out.println("(Timings vary between runs/machines; the growth pattern is what matters.)");
    }

    private static void row(String name, long singlyNs, long doublyNs) {
        System.out.printf("%-34s %12.3f %12.3f%n", name, singlyNs / 1e6, doublyNs / 1e6);
    }
}
