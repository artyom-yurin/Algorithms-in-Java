import java.util.ArrayList;

public class Heap<E>{

    private class Node{
        E value;
        int priority;

        Node(int priority, E value)
        {
            this.value = value;
            this.priority = priority;
        }
    }

    private ArrayList<Node> list;
    private int size;

    public int getSize()
    {
        return size;
    }

    Heap() {
        list = new ArrayList<>();
        size = 0;
    }

    public E max() {
        if (size > 0) {
            return list.get(0).value;
        }
        return null;
    }

    private void returnOrder(int index) {
        Node parent = list.get(index / 2);
        Node currentNode = list.get(index);
        while (parent.priority < currentNode.priority) {
            list.set(index / 2, currentNode);
            list.set(index, parent);
            index = index / 2;
            parent = list.get(index / 2);
        }
    }

    public void insertion(int priority, E value) {
        list.add(size, new Node(priority, value));
        returnOrder(size);
        size++;
    }

    private void heapify(int index) {
        int left = 2 * index;
        int right = 2 * index + 1;
        int largest = index;
        if (left < size && list.get(left).priority > list.get(largest).priority) {
            largest = left;
        }
        if (right < size && list.get(right).priority > list.get(largest).priority) {
            largest = right;
        }
        if (largest != index) {
            Node temp = list.get(index);
            list.set(index, list.get(largest));
            list.set(largest, temp);
            heapify(largest);
        }
    }

    public E removeMax() {
        E maxValue = max();

        if(size > 0)
        {
            list.set(0, list.get(size - 1));
            list.set(size - 1, null);
            size--;
            heapify(0);
        }

        return maxValue;
    }
}
