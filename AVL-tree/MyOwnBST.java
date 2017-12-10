import java.util.ArrayList;

public class MyOwnBST<Key extends Comparable<Key>> implements BinarySearchTree<Key> {

    protected class Node {
        Key value;
        Node left, right;
        Node parent;
        int height;

        Node(Key value, Node parent) {
            this.value = value;
            left = null;
            right = null;
            this.parent = parent;
            height = 1;
        }
    }

    protected Node root; // root of tree
    protected int size; // count of element

    MyOwnBST() {
        root = null;
        size = 0;
    }

    /**
     * Get size of tree
     * @return size of tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Find all entries with key k
     *
     * @param k is key of needed element
     * @return all entries with key k if they exist, null otherwise
     */
    @Override
    public ArrayList<Key> find(Key k) {

        Integer count = 0;

        Node currentNode = root;

        if (currentNode != null) {
            boolean isOver = false;
            while (!isOver) {
                if (k.compareTo(currentNode.value) > 0) {
                    if (currentNode.right != null) {
                        currentNode = currentNode.right;
                    } else {
                        isOver = true;
                    }
                } else {
                    if (currentNode.value == k) {
                        count = count + 1;
                    }

                    if (currentNode.left != null) {
                        currentNode = currentNode.left;
                    } else {
                        isOver = true;
                    }
                }
            }
        }

        if (count != 0) {
            ArrayList<Key> entries = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                entries.add(i, k);  // add element in array 'count' times
            }
            return entries;
        } else {
            return null;
        }

    }

    /**
     * Insert new element into the tree
     *
     * @param k is value of new element
     */
    @Override
    public void insert(Key k) {
        if (root != null) {
            Node currentNode = root;
            boolean isFind = false;
            while (!isFind) {
                if (k.compareTo(currentNode.value) > 0) {
                    if (currentNode.right == null) {
                        currentNode.right = new Node(k, currentNode);
                        isFind = true;
                    }
                    currentNode = currentNode.right;
                } else {
                    if (currentNode.left == null) {
                        currentNode.left = new Node(k, currentNode);
                        isFind = true;
                    }
                    currentNode = currentNode.left;
                }
            }
            balance(currentNode);
        } else {
            root = new Node(k, null);
        }
        size++;
    }

    /**
     * Balances a tree
     * (Use in AVL tree)
     *
     * @param currentNode is a start Node, with which we start balance if it need
     */
    protected void balance(Node currentNode) {
    }

    /**
     * Determine this child is left
     *
     * @param parent is prospective parent
     * @param child  is prospective left child
     * @return if child is left then true else false
     */
    private boolean isLeftChild(Node parent, Node child) {
        if (parent != null && parent.left != null && parent.left == child) {
            return true;
        }
        return false;
    }

    /**
     * Find first element with key k
     *
     * @param currentNode is current viewed node
     * @param k           is key of find element
     * @return Node of element with key k or null if tree don't have element with key k
     */
    private Node findNode(Node currentNode, Key k) {
        if (currentNode == null || currentNode.value.equals(k))
            return currentNode;

        if (currentNode.value.compareTo(k) > 0)
            return findNode(currentNode.left, k);

        return findNode(currentNode.right, k);
    }

    /**
     * Removes all entries with key k
     *
     * @param k is key of removable element
     */
    @Override
    public void remove(Key k) {
        Node currentNode = findNode(root, k);
        if (currentNode != null) {
            if (currentNode.left != null && currentNode.right != null) { // node have two child
                if (currentNode.right.left != null) { // with successor
                    Node successor = currentNode.right.left;
                    while (successor.left != null) {
                        successor = successor.left;
                    }
                    successor.parent.left = null;
                    Node successorParent = successor.parent;
                    successor.parent = currentNode.parent;
                    if (successor.parent == null) {
                        root = successor;
                    }
                    successor.left = currentNode.left;
                    successor.right = currentNode.right;
                    currentNode.left.parent = successor;
                    currentNode.right.parent = successor;
                    currentNode = successorParent;
                } else { // without successor
                    currentNode.right.parent = currentNode.parent;
                    if (currentNode.right.parent == null) {
                        root = currentNode.right;
                    }
                    currentNode.right.left = currentNode.left;
                    currentNode.left.parent = currentNode.right;
                    currentNode = currentNode.right;
                }
            } else if (currentNode.left != null) { // node have only left child
                currentNode.left.parent = currentNode.parent;
                if (currentNode.parent != null) {
                    if (isLeftChild(currentNode.parent, currentNode)) {
                        currentNode.parent.left = currentNode.left;
                    } else {
                        currentNode.parent.right = currentNode.left;
                    }
                    currentNode = currentNode.parent;
                } else {
                    root = currentNode.left;
                    currentNode = root;
                }
            } else if (currentNode.right != null) { // node have only right child
                currentNode.right.parent = currentNode.parent;
                if (currentNode.parent != null) {
                    if (isLeftChild(currentNode.parent, currentNode)) {
                        currentNode.parent.left = currentNode.right;
                    } else {
                        currentNode.parent.right = currentNode.right;
                    }
                    currentNode = currentNode.parent;
                } else {
                    root = currentNode.right;
                    currentNode = root;
                }
            } else { // node is leaf
                if (currentNode.parent != null) {
                    if (isLeftChild(currentNode.parent, currentNode)) {
                        currentNode.parent.left = null;
                    } else {
                        currentNode.parent.right = null;
                    }
                    currentNode = currentNode.parent;
                } else {
                    root = null;
                }
            }
            balance(currentNode);
            size--;
            remove(k); // remove again to remove all entries
        }
    }

    /**
     * Gets the string of the inorder traversal of the tree
     *
     * @return string of elements of tree(inorder)
     */
    @Override
    public String traverse() {
        if (root != null) {
            return inorder(root);
        }
        return "";
    }

    /**
     * Construct string of tree
     *
     * @param currentNode is current viewed node
     * @return string of elements of tree
     */
    private String inorder(Node currentNode) {
        String result = "";
        if (currentNode.left != null) {
            result += inorder(currentNode.left);
        }
        result += currentNode.value + " ";
        if (currentNode.right != null) {
            result += inorder(currentNode.right);
        }
        return result;
    }

    /**
     * gets the string of the tree in ParentLeftRight format
     *
     * @return string of the tree in ParentLeftRight format
     */
    @Override
    public String print() {
        String bst = "";
        if (root != null) {
            ArrayList<Node> openNodes = new ArrayList<>(); // array of nodes, which need for view
            bst += root.value + " ";
            if (root.left != null) {
                bst += root.left.value + " ";
                openNodes.add(openNodes.size(), root.left);
            }
            if (root.right != null) {
                bst += root.right.value;
                openNodes.add(openNodes.size(), root.right);
            }
            while (!openNodes.isEmpty()) {
                Node currentNode = openNodes.remove(0); //take first node
                boolean haveChild = false; // flag of leaf. if node is leaf, we don't print it
                String newBranch = "" + currentNode.value + " ";
                if (currentNode.left != null) {
                    newBranch += currentNode.left.value + " ";
                    haveChild = true;
                    openNodes.add(openNodes.size(), currentNode.left); // if node have left child then add it in array of open Nodes
                }
                if (currentNode.right != null) {
                    newBranch += currentNode.right.value;
                    haveChild = true;
                    openNodes.add(openNodes.size(), currentNode.right); // if node have right child then add it in array of open Nodes
                }
                if (haveChild) {
                    bst += "\n" + newBranch; // if node isn't leaf then add string of the node to global string
                }
            }
        }
        return bst;
    }

    /**
     * gets the mirror string of the tree in ParentLeftRight format
     *
     * @return mirror string of the tree in ParentLeftRight format
     */
    public String mirror() {
        String bst = "";
        if (root != null) {
            ArrayList<Node> openNodes = new ArrayList<>(); // array of nodes, which need for view
            bst += root.value + " ";
            if (root.right != null) {
                bst += root.right.value + " ";
                openNodes.add(openNodes.size(), root.right);
            }
            if (root.left != null) {
                bst += root.left.value;
                openNodes.add(openNodes.size(), root.left);
            }
            while (!openNodes.isEmpty()) {
                Node currentNode = openNodes.remove(0); //take first node
                boolean haveChild = false; // flag of leaf. if node is leaf, we don't print it
                String newBranch = "" + currentNode.value + " ";
                if (currentNode.right != null) {
                    newBranch += currentNode.right.value + " ";
                    haveChild = true;
                    openNodes.add(openNodes.size(), currentNode.right); // if node have right child then add it in array of open Nodes
                }
                if (currentNode.left != null) {
                    newBranch += currentNode.left.value;
                    haveChild = true;
                    openNodes.add(openNodes.size(), currentNode.left); // if node have left child then add it in array of open Nodes
                }
                if (haveChild) {
                    bst += "\n" + newBranch; // if node isn't leaf then add string of the node to global string
                }
            }
        }
        return bst;
    }
}
