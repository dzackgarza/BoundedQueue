/**
 * Algorithmic Runtime:
 * Since this queue is implemented with a fixed-length array, and the enqueue and dequeue pointers
 * are tracked, enqueue operations are in O(1). Since the values in the queue are not deleted, and are
 * in a sense only marked for deletion, dequeue is also in O(1).
 * <p/>
 * Memory Usage:
 * The memory usage of this queue is in O(maxSize), as the entire array is allocated
 * directly at initialization. This is a compromise, compared to an implementation that allows
 * dynamic resizing (such as using a variant of a linked list to hold the contents). At
 * steady-state, however, the memory usage is constant at (maxSize).
 * <p/>
 * Memory Throughput:
 * Throughput is optimized for in this implementation. No dynamic resizing needs to take place,
 * so the variation is the time operations take is minimized. Values are not dynamically
 * created and deleted, and are instead overwritten when needed. Once the first maxSize integers
 * have been added, the cyclic nature of the pointer lookups ensures that no new objects are
 * created unnecessarily , and no new memory needs to be allocated.
 */
public class BoundedQueue<T> {

    // The "bound" on the size.
    private final int maxSize;

    // All values will be stored in a preallocated array.
    private final T[] contents;

    // Index of where the next enqueued item should go.
    private int enqueuePointer = 0;

    // Index of where the next dequeued item will come from.
    private int dequeuePointer = 0;

    // The queue tracks how many elements it has at all times.
    private int currentNumberOfElements = 0;


    /**
     * Constructor
     *
     * @param sizeOfQueue An integer that sets the size of the queue.
     */
    public BoundedQueue(int sizeOfQueue) {

        this.maxSize = sizeOfQueue;
        this.contents = (T[]) new Object[maxSize];
    }

    /**
     * Adds an value to the end of this queue, if it is not full.
     *
     * @param in Item to be added to the queue.
     * @return Returns the item that was just added.
     * @throws Exception If and only if the queue is full.
     */
    public T enqueue(T in) throws Exception {

        if (currentNumberOfElements == maxSize) {

            throw new Exception("Queue is full.");

        } else {

            // Add the new integer to the queue
            this.contents[enqueuePointer] = in;

            // Save the current index so we can return the integer that was just added.
            int indexOfCurrentItem = enqueuePointer;

            // Keep track of how many elements are in the queue.
            currentNumberOfElements++;

            // Increment the enqueue pointer, wrapping into the appropriate range.
            enqueuePointer = indexWrap(++enqueuePointer);

            return this.contents[indexOfCurrentItem];
        }
    }

    /**
     * Returns the least recent integer that was added to this queue.
     * <p/>
     * Note - doesn't actually delete the value! We just move the pointers around,
     * and overwrite a dequeued value after cycling back around.
     *
     * @return The /least/ recent item added to the queue.
     * @throws Exception Iff there are no items left in the queue.
     */
    public T dequeue() throws Exception {

        if (currentNumberOfElements == 0) {

            // The queue is must be empty.
            throw new Exception("No integer available to dequeue.");
        }

        // Grab the least recently added value
        T ret = this.contents[dequeuePointer];

        // Increment the dequeue pointer, ensuring it's in the correct range.
        dequeuePointer = indexWrap(++dequeuePointer);

        // Keep track of how many elements are left.
        currentNumberOfElements--;

        return ret;
    }

    /**
     * Look an an integer in Z mod Z_maxSize. Used for cycling through
     * positive indices, and also includes a catch for negative values
     * (as Java preserves negatives in the dividend for mod operations).
     *
     * @param n Number to be wrapped
     * @return n's representative in the range (0, maxSize - 1)
     */
    private int indexWrap(int n) {
        return (n % maxSize >= 0 ? n % maxSize : (n % maxSize) + maxSize);
    }

    /* Bonus functions! */

    /**
     * Returns the most recently added item without dequeuing it. *
     */
    public T peek() {
        return this.contents[dequeuePointer];
    }

    /**
     * Another conventional name for "peek", added for completeness. *
     */
    public T front() {
        return this.contents[dequeuePointer];
    }

    /**
     * Returns the oldest item that is in the queue. *
     */
    public T back() {
        return this.contents[indexWrap(enqueuePointer - 1)];
    }
}
