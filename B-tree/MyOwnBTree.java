import java.util.ArrayList;

public class MyOwnBTree<Key extends Comparable<Key>> implements BTree<Key>{

    private class Node {
        ArrayList<Key> elements;
        ArrayList<Node> links;
        Node parent;

        Node(Key value, Node parent) {
            elements = new ArrayList<>(order - 1);
            links = new ArrayList<>(order);
            elements.add(0, value);
            links.add(0, null);
            links.add(1, null);
            this.parent = parent;
        }

        Node() {
            elements = new ArrayList<>(order - 1);
            links = new ArrayList<>(order);
            parent = null;
        }
    }

    private Node root; // root of tree
    private int order; // order of tree(max count of links)

    MyOwnBTree(int order) {
        this.order = order;
        root = null;
    }

    @Override
    /**
     * Find the required value in tree
     *
     * @param value is required value of Node
     * @return Value if this exists, null otherwise
     */
    public Key find(Key value) {
        int index = 0;
        Node currentNode = root;
        if (currentNode != null) {
            while (index < currentNode.elements.size()) {

                if (currentNode.elements.get(index).equals(value)) {
                    return value;
                }

                if (currentNode.elements.get(index).compareTo(value) > 0) {
                    if (currentNode.links.get(index) != null) {
                        currentNode = currentNode.links.get(index); // go to child Node
                        index = 0;
                    } else {
                        return null;
                    }
                } else {
                    index++;
                }

                if (index == currentNode.elements.size() && currentNode.links.get(index) != null) {
                    currentNode = currentNode.links.get(index); // go to right child Node
                    index = 0;
                }
            }
        }
        return null;
    }

    @Override
    /**
     * Insert new value at tree
     *
     * @param value is new value
     */
    public void insertion(Key value) {
        if (root == null) {
            root = new Node(value, null); //insert root
        } else {
            Node currentNode = root;
            int index = 0;
            boolean isInsert = false; // flag of inserting
            do {
                if (index < currentNode.elements.size() && currentNode.elements.get(index).compareTo(value) >= 0) {
                    if (currentNode.links.get(index) != null) {
                        currentNode = currentNode.links.get(index);  // go to child Node
                        index = 0;
                    } else {
                        currentNode.elements.add(index, value);  //insert at currentNode
                        currentNode.links.add(index, null);
                        isInsert = true;
                    }
                } else if (index == currentNode.elements.size()) {
                    if (currentNode.links.get(index) != null) {
                        currentNode = currentNode.links.get(index); // go to right child Node
                        index = 0;
                    } else {
                        currentNode.elements.add(index, value);  // insert large value at currentNode
                        currentNode.links.add(index, null);
                        isInsert = true;
                    }
                } else {
                    index++;
                }
            }
            while (!isInsert || (index < currentNode.elements.size() && currentNode.elements.get(index).compareTo(value) < 0));

            balance(currentNode); // reconstruct tree
        }
    }

    /**
     * Balances our Node, if this need
     *
     * @param currentNode is Node, which we balances
     */
    private void balance(Node currentNode) {
        //check that our node has extra element
        while (currentNode.elements.size() == order) {
            int midIndex = order / 2;  // find index of middle element
            Node parent;
            int newIndex = 0;

            if (currentNode.parent == null) {
                // reconstruct root
                parent = new Node(currentNode.elements.get(midIndex), null);
                root = parent;
            } else {
                parent = currentNode.parent;
                //find position for new value
                while (newIndex < parent.elements.size() && currentNode.elements.get(midIndex).compareTo(parent.elements.get(newIndex)) > 0) {
                    newIndex++;
                }
                parent.elements.add(newIndex, currentNode.elements.get(midIndex));
                parent.links.add(newIndex + 1, null);
            }

            Node leftSubNode = getSubNode(currentNode, 0, midIndex);
            Node rightSubNode = getSubNode(currentNode, midIndex + 1, order);

            leftSubNode.parent = parent;
            rightSubNode.parent = parent;

            parent.links.set(newIndex, leftSubNode);
            parent.links.set(newIndex + 1, rightSubNode);

            currentNode = parent;
        }
    }

    /**
     * Getting subNode
     * @param currentNode is main node
     * @param startIndex is start index for copying the main Node
     * @param finishIndex is finish index
     * @return subNode with value of main node from start index to finish index, not including last
     */
    private Node getSubNode(Node currentNode, int startIndex, int finishIndex) {
        Node subNode = new Node();
        subNode.elements = getSubList(currentNode.elements, startIndex, finishIndex);
        subNode.links = getSubList(currentNode.links, startIndex, finishIndex + 1);
        return subNode;
    }

    /**
     * Getting subArray
     * @param currentList is main array
     * @param startIndex is start index for copying the main array
     * @param finishIndex is finish index
     * @return subArray with value of main array from start index to finish index, not including last
     */
    private ArrayList getSubList(ArrayList currentList, int startIndex, int finishIndex) {
        ArrayList subList = new ArrayList();
        for (int i = startIndex; i < finishIndex; i++) {
            subList.add(i - startIndex, currentList.get(i));
        }
        return subList;
    }

    /**
     * Gets the string of the inorder traversal of the tree
     *
     * @return string of elements of tree(inorder)
     */
    public String traverse() {
        return inorder(root);
    }

    /**
     * Construct string of tree
     *
     * @param currentNode is current viewed node
     * @return string of elements of tree
     */
    private String inorder(Node currentNode) {
        String str = "";
        if (currentNode != null) {
            for (int i = 0; i < currentNode.elements.size(); i++) {
                str += inorder(currentNode.links.get(i));
                str += currentNode.elements.get(i) + " ";
            }
            str += inorder(currentNode.links.get(currentNode.elements.size()));
        }
        return str;
    }
}
