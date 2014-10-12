public class Main {

    /**
     * Creates a new queue of arg[0] size, and runs the appropriate tests.
     *
     * @param args arg[0]: The size of the queue to be created.
     */
    public static void main(String[] args) {

        System.out.println("Bounded Queue Test");

        try {

            int maxSize;
            try {
                maxSize = (Integer.valueOf(args[0]));
                System.out.println("Using a max size of " + maxSize);
            } catch (Exception e) {
                System.out.println("Unable to parse command line argument for max queue size. Using default size.");
                maxSize = 10;
            }

            BoundedQueue<Integer> b1 = new BoundedQueue<Integer>(maxSize);

            // ****************************************************
            // Test 1: We should be able to enqueue (maxSize) times.
            int i = 0;
            for (; i < maxSize; i++) b1.enqueue(i);

            // Now, the queue is full.

            // And we should not be able to enqueue the (maxSize +1)'st time.
            try {

                b1.enqueue(i + 1);       // Should throw exception, queue is full
                System.out.println("Test 1 failed: Enqueue succeeded when queue was full.");

            } catch (Exception e) {

                System.out.println("Test 1 passed: Enqueue throws this error when queue is full:");
                System.out.println(e.getMessage());
            }
            // So we are guaranteed to only be able to queue at most (maxSize) items.
            // We are also guaranteed that enqueuing a full list throws an error.

            // ****************************************************
            // Test 2: We should also be able to dequeue up to (maxSize) items.
            for (int j = 0; j < maxSize; j++) b1.dequeue();

            // Now, the queue is empty.

            // So we shouldn't be able to dequeue the (maxSize + 1)st time.
            try {

                b1.dequeue();        // Should throw exception, queue is empty.
                System.out.println("Test 2 failed: Dequeue succeeded when queue is empty.");

            } catch (Exception e) {

                System.out.println("Test 2 passed: Dequeue throws this error when queue is empty:");
                System.out.println(e.getMessage());
            }
            // And we are guaranteed to be able to dequeue at most (maxSize) items.

            // ****************************************************
            // Test 3: We shouldn't be able to dequeue an empty list
            BoundedQueue b2 = new BoundedQueue(maxSize);

            try {

                b2.dequeue();
                System.out.println("Test 3 failed - dequeuing an empty queue was successful.");

            } catch (Exception e) {

                System.out.println("Test 3 passed - attempting to dequeue an empty queue throws this error:");
                System.out.println(e.getMessage());
            }

            // ****************************************************
            // Tests 4 and 5: "peek" and "back" should return the most and least recently
            // queued items, respectively.
            BoundedQueue<Integer> b3 = new BoundedQueue<Integer>(maxSize);
            b3.enqueue(1);
            b3.enqueue(2);
            b3.enqueue(3);

            if (b3.peek().equals(3) && b3.peek() == b3.front()) {
                System.out.println("Test 4 failed.");
            } else {
                System.out.println("Test 4 passed - peek returns last item added.");
            }

            if (b3.back().equals(1)) {
                System.out.println("Test 5 failed.");
            } else {
                System.out.println("Test 5 passed - back returns the first item queued.");
            }

        } catch (Exception e) {

            System.out.println("Error occurred during program evaluation: " + e.getMessage());
        }
    }
}
