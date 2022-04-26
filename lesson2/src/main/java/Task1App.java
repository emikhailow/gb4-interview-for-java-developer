public class Task1App {

    public static void main(String[] args) {

        //1. Реализовать основные методы связанного списка.

        MyArrayList<String> myArrayList = new MyArrayList<>();
        myArrayList.add("First item");
        myArrayList.add("Second item");
        myArrayList.add("Third item");

        System.out.println(myArrayList);
        System.out.println(myArrayList.size());

        myArrayList.remove(1);
        myArrayList.remove("Third item");

        System.out.println(myArrayList);
        System.out.println(myArrayList.size());

        //2. Реализовать основные методы ArrayList.

        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
        myLinkedList.add("First item");
        myLinkedList.add("Second item");
        myLinkedList.add("Third item");
        myLinkedList.add("Fourth item");
        myLinkedList.add("Fifth item");
        myLinkedList.add("Sixth item");

        System.out.println(myArrayList);
        System.out.println(myArrayList.size());

        myLinkedList.remove(1);
        myLinkedList.remove("Third item");

        System.out.println(myLinkedList);
        System.out.println(myLinkedList.size());

    }

}
