/**
 * Created by Josue on 11/16/2016.
 */
public class AbstractQueue {
    protected Object[] elements;
    protected int max;
    protected int rear;
    protected int front;
    protected int nElements;

    public AbstractQueue(int max) {
        this.elements = new Object[max];
        this.max = max;
        this.rear = -1; // last inserted item
        this.front = 0; // first inserted item
        this.nElements = 0;
    }

    public AbstractQueue() {
        this.max = 1000;
        this.elements = new Object[this.max];
        this.rear = -1; // last inserted item
        this.front = 0; // first inserted item
    }


    protected Object remove() {
        return null;
    }

    protected void insert(Object element) {
    }

    protected void clone(MQueue queue) {
        this.elements = queue.elements;
        this.max = queue.max;
        this.rear = queue.rear; // last inserted item
        this.front = queue.front; // first inserted item
        this.nElements = queue.nElements;
    }

    protected Object peekFront() {
        if (!this.isEmpty())
            return this.elements[this.front];

        return null;
    }

    protected boolean isEmpty() {
        return this.nElements == 0;
    }

    protected boolean isFull() {
        return this.nElements == this.max;
    }

    protected int size() {
        return this.nElements;
    }
}
