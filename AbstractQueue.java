/**
 * Created by Josue on 11/16/2016.
 */
public abstract class AbstractQueue {
    protected Object[] elements;
    protected int max;
    protected int rear;
    protected int front;
    protected int nElements;

    /**
     * Constructor of the stack
     *
     * @param max: maximum number of items that the stack can store
     */
    public AbstractQueue(int max) {
        this.elements = new Object[max];
        this.max = max;
        this.rear = -1; // last inserted item
        this.front = 0; // first inserted item
        this.nElements = 0;
    }

    /**
     * Constructor without max size
     */
    public AbstractQueue() {
        this.max = 1000;
        this.elements = new Object[this.max];
        this.rear = -1; // last inserted item
        this.front = 0; // first inserted item
    }

    /**
     * Add element to the Queue
     * TODO: implement when extended
     * @param element: element to be added
     */
    protected void insert(Object element) {
    }

    /**
     * Remove the first element
     * TODO: implement when extended
     * @return the element removed
     */
    protected Object remove() {
        return null;
    }

    /**
     * This will let us get the element in the front
     * but will not remove it from the queue
     * @return element in the front
     */
    protected Object peekFront() {
        if (!this.isEmpty())
            return this.elements[this.front];

        return null;
    }

    /**
     * Checks if there are no elements in the queue
     * @return true if no elements
     */
    protected boolean isEmpty() {
        return this.nElements == 0;
    }

    /**
     * Checks if the queue is full
     * @return true if it met the max size allowed
     */
    protected boolean isFull() {
        return this.nElements == this.max;
    }

    /**
     * Gets the elements number there are in the queue
     * @return numbers of elements in the queue
     */
    protected int size() {
        return this.nElements;
    }
}
