import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

class Tree<T extends Comparable<T>>{
  private int size;
  private Node<T> root;

  /**
   * A list element
   */
    public static class Node<T>{
      public T data;
      public Node<T> rc;
      public Node<T> lc;

      public Node(T data){
          this.data = data;
          rc = null;
          lc = null;
      }
    }

    /**
     * Constructor for a tree
     */
    public Tree(){
        root = null;
        size = 0;
    }
    /**
     * Searches the tree for value
     * @param  elem search key
     * @return      true if found, false if not
     */
    public boolean search(T elem){
        Node<T> searchNode = root;
        while(searchNode != null){
          int val = elem.compareTo(searchNode.data);
          if(val==0){
            return true;
          }
          else if(val > 0){
            searchNode = searchNode.rc;
          }
          else if(val < 0){
            searchNode = searchNode.lc;
          }
        }
        return false;
    }
    /**
     * Adds an element to tree
     * @param  elem the element
     * @return      true if succesfully added, else false
     */
    public boolean insert(T elem){
      if(search(elem)){
        return false;
      }
      Node<T> newElem = new Node<>(elem);
      Node<T> searchNode = root;
      boolean inserted = false;
      if(root == null){
        root = newElem;
        inserted = true;
      }
      while(searchNode != null && !inserted){
        int value = elem.compareTo(searchNode.data);
        if(value >0){
          if(searchNode.rc == null){
            searchNode.rc = newElem;
            break;
          }
          searchNode = searchNode.rc;
        }
        else{
          if(searchNode.lc == null){
            searchNode.lc = newElem;
            break;
          }
          searchNode = searchNode.lc;
        }
      }
      size++;
      return true;
    }
    /**
     * @return the number of elements in the tree
     */
    public int size(){
      return size;
    }
    /**
     * @return the height of the tree
     */
    public int height(){
      if(size <= 1){
        return 0;
      }
      return heightHelper(root);
    }
    /**
     * Helper method to recursivelyh calculate height of tree
     * @param  node
     * @return  the height of the tree
     */
    private int heightHelper(Node<T> node){
      if(node == null){
        return 0;
      }
      if(isLeaf(node)){
        return 0;
      }
      int left = heightHelper(node.lc);
      int right = heightHelper(node.rc);
      return Math.max(left,right) + 1;
    }
    /**
     * Check if node is a leaf
     * @param  check node to check
     * @return       true if leaf else false
     */
    private boolean isLeaf(Node<T> check){
      return (check.lc == null && check.rc==null);
    }
    /**
     * @return the number of leaves
     */
    public int leaves(){
      if(size == 0){
        return 0;
      }
      return leavesHelper(root);
    }
    /**
     * Helper method to recursively count how many leaves the tree has.
     * @param  node
     * @return      returns the number of leaves.
     */
    private int leavesHelper(Node<T> node){
      if(node == null){
        return 0;
      }
      if(isLeaf(node)){
        return 1;
      }
      return leavesHelper(node.lc)+leavesHelper(node.rc);
    }
    /**
     * Describes the elements of the tree
     * @return elements of tree in ascending order "[x,y,z]"
     */
    public String toString(){
      if(size == 0){
        return "[]";
      }
        return Arrays.toString(inorder(root, new ArrayList<T>()).toArray());
    }
    /**
     * A recursive function of an In-order traversal algorithm
     * @param  node
     * @return      an array in ascending order e.g [1,2,3,4,5];
     */
    public ArrayList<T> inorder(Node<T> node, ArrayList<T> list){
        if(node.lc != null){
          inorder(node.lc,list);
        }
        list.add(node.data);
        if(node.rc != null){
          inorder(node.rc, list);
        }
        return list;
    }
}
