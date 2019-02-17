public interface Stack<T> {
/**
 * Adds an element on the top of the stack.
 * @param elem the element to be inserted.
 */
void push(T elem);
/**
 * Removes and return the top element of the stack.
 * Throws EmptyStackException if the stack is empty.
 */
T pop();
/**
 * Returns the top element of the stack.
 * Throws EmptyStackException if the stack is empty.
 */
T top();
/**
 * Returns the number of elements in the stack.
 * @return number of elements
 */
int size();
/**
 * @return true if stack is empty.
 */
boolean isEmpty();
}
