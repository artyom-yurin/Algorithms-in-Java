import java.time.temporal.ChronoUnit;
import java.util.Stack;

public class MyOwnAVLTree <Key extends Comparable<Key>> extends MyOwnBST<Key> {

    /**
     * Balances a tree
     * @param currentNode is a start Node, with which we start balance if it need
     */
    protected void balance(Node currentNode) {
        while (needBalance(currentNode)) {
            Node firstNode = currentNode;
            while (firstNode.parent != null && difference(firstNode) <= 1) {
                firstNode = firstNode.parent;
            }

            Node secondNode = getMaxBranch(firstNode);
            Node thirdNode = getMaxBranch(secondNode);

            spin(firstNode, secondNode, thirdNode);
        }
    }

    /**
     * Get branch with most height
     * @param currentNode is node for which we get branch
     * @return root Node of max branch
     */
    private Node getMaxBranch(Node currentNode)
    {
        if(currentNode.right != null && currentNode.left != null)
        {
            if(currentNode.right.height > currentNode.left.height)
            {
                return currentNode.right;
            }
            else
            {
                return currentNode.left;
            }
        }
        else if(currentNode.right != null)
        {
            return currentNode.right;
        }
        else if (currentNode.left != null)
        {
            return currentNode.left;
        }
        else
        {
            return null;
        }

    }

    /**
     * Check do we have disbalance
     * @param currentNode is Node where we begin checking
     * @return need we return balance or not
     */
    private boolean needBalance(Node currentNode)
    {
        boolean needBalance = false;
        if (currentNode != null)
        {
            recalculate(currentNode);
            if(difference(currentNode) > 1)
            {
                needBalance = true;
            }
            while(currentNode.parent != null)
            {
                currentNode = currentNode.parent;
                recalculate(currentNode);
                if(difference(currentNode) > 1)
                {
                    needBalance = true;
                }
            }
        }
        return needBalance;
    }

    /**
     * Set chidren for parent
     * @param parent is some Node
     * @param left is new left child of parent
     * @param right is new right child of parent
     */
    private void setChildren(Node parent, Node left, Node right)
    {
        parent.left = left;
        parent.right = right;
        if(parent.right != null)
        {
            right.parent = parent;
        }
        if(parent.left != null)
        {
            left.parent = parent;
        }
    }

    /**
     * Set parent of oldChild to newChild
     * @param newChild is some Node
     * @param oldChild is another Node
     */
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

    /**
     * Trinode Restructuring
     * @param firstNode is parent of second Node. This Node has disbalance
     * @param secondNode is parent of third Node
     * @param thirdNode is lower element
     */
    private void spin(Node firstNode, Node secondNode, Node thirdNode)
    {
        if (thirdNode.value.compareTo(secondNode.value) >= 0 && secondNode.value.compareTo(firstNode.value) >= 0) { // small left rotate
            newParent(secondNode, firstNode);
            firstNode.right = secondNode.left;
            setChildren(secondNode, firstNode, thirdNode);
            recalculate(firstNode);
            recalculate(thirdNode);
            recalculate(secondNode);
        } else if (thirdNode.value.compareTo(secondNode.value) <= 0 && secondNode.value.compareTo(firstNode.value) <= 0) { // small right rotate
            newParent(secondNode, firstNode);
            firstNode.left = secondNode.right;
            setChildren(secondNode, thirdNode, firstNode);
            recalculate(firstNode);
            recalculate(thirdNode);
            recalculate(secondNode);
        } else if (secondNode.value.compareTo(thirdNode.value) >= 0 && thirdNode.value.compareTo(firstNode.value) >= 0) { // big left rotate
            newParent(thirdNode, firstNode);
            firstNode.right = thirdNode.left;
            secondNode.left = thirdNode.right;
            setChildren(thirdNode, firstNode, secondNode);
            recalculate(firstNode);
            recalculate(secondNode);
            recalculate(thirdNode);
        } else if (secondNode.value.compareTo(thirdNode.value) <= 0 && thirdNode.value.compareTo(firstNode.value) <= 0) { // big right rotate
            newParent(thirdNode, firstNode);
            firstNode.left = thirdNode.right;
            secondNode.right = thirdNode.left;
            setChildren(thirdNode, secondNode, firstNode);
            recalculate(firstNode);
            recalculate(secondNode);
            recalculate(thirdNode);
        }
    }

    /**
     * Calculate difference between left and right subtree
     * @param currentNode is node, for which we calculate difference
     * @return difference between left and right heights of nodes
     */
    private int difference(Node currentNode) {
        if (currentNode.left != null && currentNode.right != null) {
            return Math.abs(currentNode.left.height - currentNode.right.height);
        } else if (currentNode.right != null) {
            return currentNode.right.height;
        } else if (currentNode.left != null) {
            return currentNode.left.height;
        }
        return 0;
    }

    /**
     * Recalculate height of current node
     *
     * @param currentNode is node which need recalculate
     */
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

    /**
     * Count number of smallest elements for all Nodes
     * @return number of smallest elements for all
     */
    public int countingSmallestElements()
    {
        if(root == null)
        {
            return 0;
        }
        Stack<Node> stack = new Stack<>();
        Node currentNode = root.left;
        int i = 0; // current number of smallest elements
        int count = 0; //sum
        stack.push(root);
        while (!stack.empty())
        {
            if(currentNode == null)
            {
                Node elem = stack.pop(); // go to right branch
                currentNode = elem.right;
                count += i;
                i += 1;
            }

            if(currentNode != null)
            {
                stack.push(currentNode);  // go to left branch
                currentNode = currentNode.left;
            }
        }
        return count;
    }

    public void postorder()
    {
        if(root == null)
        {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        Node currentNode = null;
        stack1.push(root);
        while(!stack1.isEmpty())
        {
            currentNode = stack1.pop();
            if (currentNode.left != null)
            {
                stack1.push(currentNode.left);
            }
            if (currentNode.right != null)
            {
                stack1.push(currentNode.right);
            }
            stack2.push(currentNode);
        }
        while(!stack2.isEmpty())
        {
            System.out.println(stack2.pop().value);
        }
    }
}
