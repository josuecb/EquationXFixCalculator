/**
 * Created by Josue on 11/16/2016.
 */
public abstract class AbstractStack {
    protected int top; // the position of the last element stored (on the top)
    protected Object[] elements; // Objects in the stack
    protected int max;

    /**
     * Constructor of the stack
     *
     * @param max: maximum number of items that the stack can store
     */
    public AbstractStack(int max) {
        this.elements = new Object[max];
        this.max = max;
        this.top = -1;
    }


    /**
     * Constructor without max size
     */
    public AbstractStack() {
        this.max = 1000;
        this.elements = new Object[this.max];
        this.top = -1;
    }

    /**
     * Add element to stack
     * TODO: implement when extended
     *
     * @param element: element to be added
     */
    protected void push(Object element) {

    }

    /**
     * @param position: element position
     * @return specific element from stack according to the input position
     */
    protected Object peekN(int position)  // return item at index n
    {
        return this.elements[position];
    }

    /**
     * remove last element from stack
     *
     * @return Object: last element from stack
     */
    protected Object pop() {
        return null;
    }

    /**
     * Get the last element from stack
     * TODO: implement when extended
     *
     * @return Object: last element from stack
     */
    protected Object peek() {
        return null;
    }

    /**
     * Check whether the stack is empty or not
     * TODO: implement when extended
     *
     * @return boolean value
     */
    protected boolean isEmpty() {
        return false;
    }

    /**
     * Checks whether the stack is full or not
     * TODO: implement when extended
     *
     * @return boolean value
     */
    protected boolean isFull() {
        return false;
    }

    /**
     * Gets the current size of the stack
     * TODO: implement when extended
     *
     * @return int current size of the stack
     */
    protected int size() {
        return this.top + 1;
    }

    /**
     * Displays the stack values
     * TODO: implement when extended
     */
    public void display() {

    }

    /**
     * Clones another stack
     * TODO: implement when extended
     *
     * @param stack: the stack to be cloned
     */
    protected void clone(AbstractStack stack) {
        this.elements = stack.elements;
        this.max = stack.max;
        this.top = stack.top;
    }

    /**
     * Inverse the stack
     * TODO: implement when extended
     */
    protected void reverse() {

    }
}
