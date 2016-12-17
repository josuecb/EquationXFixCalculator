/**
 * Created by Josue on 12/12/2016.
 */
public class MStack extends AbstractStack {
    public MStack() {
        super();    // call AbstractStack constructor
    }

    @Override
    public void push(Object element) {
        this.elements[++this.top] = element; // store and increment the size of elements by 1
    }

    @Override
    public Object pop() {
        return this.elements[this.top--]; // remove and decrement element size by 1
    }

    @Override
    public Object peek() {
        return this.elements[this.top]; // return last element
    }

    @Override
    public boolean isEmpty() {
        return (this.top == -1);
    }

    @Override
    public boolean isFull() {
        return this.top == (this.max - 1);
    }

    @Override
    public void display() {
        for (int index = 0; index <= this.top; index++)
            System.out.print(this.elements[index].toString() + " ");
        System.out.println();
    }

    /**
     * Reverse the stack position (We will use it many times)
     */
    public void reverse() {
        MStack temp = new MStack();

        // reverse it
        while (!this.isEmpty())
            temp.push(this.pop());

        // clones the temp stack
        this.clone(temp);
    }

    /**
     * Display a sentence before every value in the stack
     *
     * @param word: the word you want to add at the begining of the sentence
     */
    public void displayStack(String word) {
        System.out.print(word);
        System.out.print("Stack(bottom-- > top): ");
        for (int j = 0; j < size(); j++) {
            System.out.print(peekN(j));
            System.out.print(" ");
        }
        System.out.println();
    }
}
