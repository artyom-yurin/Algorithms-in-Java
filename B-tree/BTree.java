public interface BTree<Key extends Comparable<Key>> {

    void insertion(Key value); // insert new value

    Key find(Key value); // find value
}
