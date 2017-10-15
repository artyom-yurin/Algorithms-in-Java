public class Node {
    int value;
    int height;
    Node left;
    Node right;
    Node parent;


    Node(int value, Node parent)
    {
        this.value = value;
        this.parent = parent;
        height = 1;
    }
}
