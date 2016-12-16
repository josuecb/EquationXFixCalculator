/**
 * Created by Josue on 11/16/2016.
 */
public class MQueue extends AbstractQueue {
    public MQueue() {
        super();
    }

    public MQueue(String words) {
        char[] letters = words.toCharArray();

        for (char letter : letters)
            this.insert(letter);
    }

    protected Object remove() {
        Object temp = this.elements[this.front];
        this.elements[this.front++] = null;
        if (this.front == this.max)
            this.front = 0;

        this.nElements--;
        return temp;
    }

    protected void insert(Object element) {
        if (this.rear == this.max - 1)
            this.rear = -1;
        this.elements[++this.rear] = element;
        this.nElements++;
    }


}
