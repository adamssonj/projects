import java.util.NoSuchElementException;
import java.util.EmptyStackException;
/**
 * A singly linked list.
 *
 * @author Johan Adamsson
 * @version 2019-01-22
 */
public class LinkedList<T> implements Stack<T>{
    private ListElement<T> first;   // First element in list.
    private ListElement<T> last;    // Last element in list.
    private int size;               // Number of elements in list.

    /**
     * A list element.
     */
    private static class ListElement<T> {
        public T data;
        public ListElement<T> next;

        public ListElement(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Creates an empty list.
     */
    public LinkedList() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Inserts the given element at the beginning of this list.
     *
     * @param element An element to insert into the list.
     */
    public void addFirst(T element) {
        ListElement<T> l = new ListElement<>(element);
        if(isEmpty()){
          first = l;
          last = l;
        }
        else{
          l.next = first;
          first = l;
        }
        size++;
    }

    /**
     * Inserts the given element at the end of this list.
     *
     * @param element An element to insert into the list.
     */
    public void addLast(T element) {
      ListElement<T> l = new ListElement<>(element);
      if(last == null){
        first = l;
        last = first;
      }
      else{
        last.next = l;
        last = l;
      }
      size++;
    }

    /**
     * @return The head of the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public T getFirst() {
      if(size == 0){
        throw new NoSuchElementException();
      }
        return first.data;
    }

    /**
     * @return The tail of the list.
     * @throws NoSuchElementException if the list is empty.
     */
    public T getLast() {
        if(size == 0){
          throw new NoSuchElementException();
        }
        return last.data;
    }

    /**
     * Returns an element from a specified index.
     *
     * @param index A list index.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public T get(int index) {
      ListElement<T> ret = first;
          if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
          }
          else{
            for(int i = 0; i < index; i++){
              ret = ret.next;
            }
          }
        return ret.data;
    }

    /**
     * Removes the first element from the list.
     *
     * @return The removed element.
     * @throws NoSuchElementException if the list is empty.
     */
    public T removeFirst() {
      ListElement<T> ret = first;
      if(size == 0){
        throw new NoSuchElementException();
      }
      else{
        first = first.next;
        size--;
      }
      if(size == 0){
        clear();
      }
        return ret.data;
    }

    /**
     * Removes all of the elements from the list.
     */
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * @return The number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Note that by definition, the list is empty if both first and last
     * are null, regardless of what value the size field holds (it should
     * be 0, otherwise something is wrong).
     *
     * @return <code>true</code> if this list contains no elements.
     */
    public boolean isEmpty() {
        return first == null && last == null;
    }

    /**
     * Creates a string representation of this list. The string
     * representation consists of a list of the elements enclosed in
     * square brackets ("[]"). Adjacent elements are separated by the
     * characters ", " (comma and space). Elements are converted to
     * strings by the method toString() inherited from Object.
     *
     * Examples:
     *  "[1, 4, 2, 3, 44]"
     *  "[]"
     *
     * @return A string representing the list.
     */
    public String toString() {
      if(size == 0){
        return "[]";
      }
      StringBuilder strB = new StringBuilder();
      ListElement<T> temp = first;
          for(int i = 0;i < size;i++){
            strB.append(temp.data);
            temp = temp.next;
            if(i != size-1){
              strB.append(", ");
            }
          }
        String str = "["+ strB.toString() + "]";
        return str;
    }
    /**
     * Adds an element on the top of the stack.
     * @param elem the element to be inserted.
     */
    public void push(T elem){
      addFirst(elem);
    }
    /**
     * Removes and return the top element of the stack.
     * Throws EmptyStackException if the stack is empty.
     */
    public T pop(){
      if(isEmpty()){
        throw new EmptyStackException();
      }
      return removeFirst();
    }
    /**
     * Returns the top element of the stack.
     * Throws EmptyStackException if the stack is empty.
     */
    public T top(){
      if(isEmpty()){
        throw new EmptyStackException();
      }
      return getFirst();
    }
}
