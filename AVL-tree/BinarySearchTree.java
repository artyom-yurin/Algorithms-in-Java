import java.util.ArrayList;

public interface BinarySearchTree<K extends Comparable<K>> {
    ArrayList<K> find(K k); // returns all entries with key k if they exist, null otherwise

    void insert(K k); // inserts an entry with key k

    void remove(K k); // removes all entries with key k

    String traverse(); // gets the string of the inorder traversal of the tree

    String print(); // gets the string of the tree in ParentLeftRight format
}
