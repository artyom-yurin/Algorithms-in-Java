public class Main {
    public static void main(String[] args) {
        AVL tree = new AVL();
        tree.addNewElement(4);
        tree.addNewElement(3);
        tree.addNewElement(7);
        tree.addNewElement(2);
        tree.addNewElement(6);
        tree.addNewElement(9);
        tree.addNewElement(5);
        tree.addNewElement(8);

        System.out.println(tree.print());
        tree.remove(4);
        System.out.println(tree.print());
    }
}
