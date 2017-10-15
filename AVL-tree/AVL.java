public class AVL {
    private Node root;
    private int size;

    public int getHeigth() {
        return root.height;
    }

    public int getSize() {
        return size;
    }

    public String print() {
        return printElement(root);
    }

    private String printElement(Node currentNode) {
        String str = "";
        if (currentNode.left != null) {
            str += printElement(currentNode.left);
        }
        str += currentNode.value + " ";
        if (currentNode.right != null) {
            str += printElement(currentNode.right);
        }
        return str;
    }

    public void addNewElement(int value) {
        ++size;
        if (root == null) {
            root = new Node(value, null);
        } else {
            Node currentNode = root;
            boolean isFind = false;
            while (!isFind) {
                if (currentNode.value < value) {
                    if (currentNode.right == null) {
                        currentNode.right = new Node(value, currentNode);
                        isFind = true;
                    }
                    currentNode = currentNode.right;
                } else {
                    if (currentNode.left == null) {
                        currentNode.left = new Node(value, currentNode);
                        isFind = true;
                    }
                    currentNode = currentNode.left;
                }
            }

            recalculatePath(currentNode);

            convertToATL(currentNode);
        }
    }

    private int deference(Node currentNode) {
        if (currentNode.left != null && currentNode.right != null) {
            return Math.abs(currentNode.right.height - currentNode.left.height);
        } else if (currentNode.left == null && currentNode.right != null) {
            return currentNode.right.height;
        } else if (currentNode.left != null) {
            return currentNode.left.height;
        } else {
            return 1;
        }
    }

    private void convertToATL(Node currentNode) {
        Node firstNode = currentNode;
        Node secondNode = currentNode;
        Node thirdNode = currentNode;
        while (firstNode.parent != null && deference(firstNode.parent) <= 1) {
            thirdNode = secondNode;
            secondNode = firstNode;
            firstNode = firstNode.parent;
        }

        if (firstNode.parent != null) {
            thirdNode = secondNode;
            secondNode = firstNode;
            firstNode = firstNode.parent;
        }

        if (deference(firstNode) > 1) {
            if (thirdNode.value >= secondNode.value && secondNode.value >= firstNode.value) {
                newParent(secondNode, firstNode);
                firstNode.right = secondNode.left;
                secondNode.left = firstNode;
                secondNode.right = thirdNode;
                firstNode.parent = secondNode;
                thirdNode.parent = secondNode;
                recalculate(firstNode);
                recalculate(thirdNode);
                recalculatePath(secondNode);
            } else if (thirdNode.value <= secondNode.value && secondNode.value <= firstNode.value) {
                newParent(secondNode, firstNode);
                firstNode.left = secondNode.right;
                secondNode.left = thirdNode;
                secondNode.right = firstNode;
                firstNode.parent = secondNode;
                thirdNode.parent = secondNode;
                recalculate(firstNode);
                recalculate(thirdNode);
                recalculatePath(secondNode);
            } else if (secondNode.value >= thirdNode.value && thirdNode.value >= firstNode.value) {
                newParent(thirdNode, firstNode);
                firstNode.right = thirdNode.left;
                secondNode.left = thirdNode.right;
                thirdNode.left = firstNode;
                thirdNode.right = secondNode;
                firstNode.parent = thirdNode;
                secondNode.parent = thirdNode;
                recalculate(firstNode);
                recalculate(secondNode);
                recalculatePath(thirdNode);
            } else if (secondNode.value <= thirdNode.value && thirdNode.value <= firstNode.value) {
                newParent(thirdNode, firstNode);
                firstNode.left = thirdNode.right;
                secondNode.right = thirdNode.left;
                thirdNode.left = secondNode;
                thirdNode.right = firstNode;
                firstNode.parent = thirdNode;
                secondNode.parent = thirdNode;
                recalculate(firstNode);
                recalculate(secondNode);
                recalculatePath(thirdNode);
            }
        }
    }

    private void newParent(Node newChild, Node oldChild) {
        Node parent = oldChild.parent;
        newChild.parent = parent;
        if (parent != null) {
            if (parent.left != null && parent.left == oldChild) {
                parent.left = newChild;
            } else {
                parent.right = newChild;
            }
        } else {
            root = newChild;
        }
    }

    private void recalculatePath(Node startNode) {
        recalculate(startNode);
        while (startNode.parent != null) {
            startNode = startNode.parent;
            recalculate(startNode);
        }
    }

    private void recalculate(Node currentNode) {
        if (currentNode.left != null && currentNode.right != null) {
            currentNode.height = 1 + Math.max(currentNode.right.height, currentNode.left.height);
        } else if (currentNode.left == null && currentNode.right != null) {
            currentNode.height = currentNode.right.height + 1;
        } else if (currentNode.left != null) {
            currentNode.height = currentNode.left.height + 1;
        } else {
            currentNode.height = 1;
        }
    }

    private Node findNode(int key) {
        Node currentNode = root;
        while (currentNode != null && currentNode.value != key) {
            if (currentNode.value < key) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }
        return currentNode;
    }

    private void spin(Node firstNode, Node secondNode, Node thirdNode) {
        if (thirdNode.value >= secondNode.value && secondNode.value >= firstNode.value) {
            newParent(secondNode, firstNode);
            firstNode.right = secondNode.left;
            secondNode.left = firstNode;
            secondNode.right = thirdNode;
            firstNode.parent = secondNode;
            thirdNode.parent = secondNode;
            recalculate(firstNode);
            recalculate(thirdNode);
            recalculate(secondNode);
        } else if (thirdNode.value <= secondNode.value && secondNode.value <= firstNode.value) {
            newParent(secondNode, firstNode);
            firstNode.left = secondNode.right;
            secondNode.left = thirdNode;
            secondNode.right = firstNode;
            firstNode.parent = secondNode;
            thirdNode.parent = secondNode;
            recalculate(firstNode);
            recalculate(thirdNode);
            recalculate(secondNode);
        } else if (secondNode.value >= thirdNode.value && thirdNode.value >= firstNode.value) {
            newParent(thirdNode, firstNode);
            firstNode.right = thirdNode.left;
            secondNode.left = thirdNode.right;
            thirdNode.left = firstNode;
            thirdNode.right = secondNode;
            firstNode.parent = thirdNode;
            secondNode.parent = thirdNode;
            recalculate(firstNode);
            recalculate(secondNode);
            recalculate(thirdNode);
        } else if (secondNode.value <= thirdNode.value && thirdNode.value <= firstNode.value) {
            newParent(thirdNode, firstNode);
            firstNode.left = thirdNode.right;
            secondNode.right = thirdNode.left;
            thirdNode.left = secondNode;
            thirdNode.right = firstNode;
            firstNode.parent = thirdNode;
            secondNode.parent = thirdNode;
            recalculate(firstNode);
            recalculate(secondNode);
            recalculate(thirdNode);
        }
    }

    public void remove(int key) {
        Node currentNode = findNode(key);
        if (currentNode != null) {
            if (currentNode.left != null && currentNode.right != null) {

                if (currentNode.right.left != null) {
                    Node successor = currentNode.right.left;
                    while (successor.left != null) {
                        successor = successor.left;
                    }
                    successor.parent.left = null;
                    Node oldParent = successor.parent;
                    newParent(successor, currentNode);
                    successor.left = currentNode.left;
                    successor.right = currentNode.right;
                    currentNode.left.parent = successor;
                    currentNode.right.parent = successor;

                    recalculate(oldParent);
                    returnBalance(oldParent, true);
                    while (oldParent.parent != null) {
                        boolean isLeftChild = isLeftChild(oldParent, oldParent.parent);
                        oldParent = oldParent.parent;
                        recalculate(oldParent);
                        returnBalance(oldParent, isLeftChild);
                    }
                } else {
                    newParent(currentNode.right, currentNode);
                    currentNode.right.left = currentNode.left;
                    currentNode.left.parent = currentNode.right;

                    currentNode = currentNode.right;
                    recalculate(currentNode);
                    returnBalance(currentNode, false);
                    while (currentNode.parent != null) {
                        boolean isLeftChild = isLeftChild(currentNode, currentNode.parent);
                        currentNode = currentNode.parent;
                        recalculate(currentNode);
                        returnBalance(currentNode, isLeftChild);
                    }
                }
                --size;

            } else if (currentNode.left != null) {
                boolean isLeftChild = isLeftChild(currentNode, currentNode.parent);
                newParent(currentNode.left, currentNode);
                currentNode = currentNode.parent;
                if (currentNode != null) {
                    recalculate(currentNode);
                    returnBalance(currentNode, isLeftChild);
                    while (currentNode.parent != null) {
                        isLeftChild = isLeftChild(currentNode, currentNode.parent);
                        currentNode = currentNode.parent;
                        recalculate(currentNode);
                        returnBalance(currentNode, isLeftChild);
                    }
                }
                --size;
            } else if (currentNode.right != null) {
                boolean isLeftChild = isLeftChild(currentNode, currentNode.parent);
                newParent(currentNode.right, currentNode);
                currentNode = currentNode.parent;
                if (currentNode != null) {
                    recalculate(currentNode);
                    returnBalance(currentNode, isLeftChild);
                    while (currentNode.parent != null) {
                        isLeftChild = isLeftChild(currentNode, currentNode.parent);
                        currentNode = currentNode.parent;
                        recalculate(currentNode);
                        returnBalance(currentNode, isLeftChild);
                    }
                }
                --size;
            } else {
                Node parent = currentNode.parent;
                if (parent != null) {
                    boolean isLeftChild = false;
                    if (parent.left != null && parent.left == currentNode) {
                        parent.left = null;
                        isLeftChild = true;
                    } else {
                        parent.right = null;
                    }
                    recalculate(parent);
                    returnBalance(parent, isLeftChild);
                    while (parent.parent != null) {
                        isLeftChild = isLeftChild(parent, parent.parent);
                        parent = parent.parent;
                        recalculate(parent);
                        returnBalance(parent, isLeftChild);
                    }
                    size--;
                } else {
                    root = null;
                    size = 0;
                }
            }
        }
    }

    private boolean isLeftChild(Node child, Node parent) {
        if (parent != null && parent.left != null && parent.left == child) {
            return true;
        }
        return false;
    }

    private void returnBalance(Node currentNode, boolean removeLeftChild) {
        if (deference(currentNode) > 1) {
            Node firstNode = currentNode;
            Node secondNode;
            Node thirdNode;
            if (removeLeftChild) {
                secondNode = currentNode.right;
                if (secondNode.right != null) {
                    thirdNode = secondNode.right;
                } else {
                    thirdNode = secondNode.left;
                }
            } else {
                secondNode = currentNode.left;
                if (secondNode.right != null) {
                    thirdNode = secondNode.right;
                } else {
                    thirdNode = secondNode.left;
                }
            }
            spin(firstNode, secondNode, thirdNode);
        }
    }
}
