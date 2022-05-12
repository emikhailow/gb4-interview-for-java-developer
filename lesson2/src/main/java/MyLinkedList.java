import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{

    private class Node {
        T value;
        Node next;
        Node prev;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    public Node first;
    private Node last;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<T>{
        Node current = new Node(null, first);

        @Override
        public boolean hasNext() {
            return current.getNext() != null;
        }

        @Override
        public T next() {
            current = current.getNext();
            return current.getValue();
        }
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return size;
    }

    private void insertFirst(T item){
        Node newNode = new Node(item, first);
        if(isEmpty()){
            last = newNode;
        } else {
            first.setPrev(newNode);
        }
        first = newNode;
        size++;
    }

    private void insertLast(T item){
        Node newNode = new Node(item, null, last);
        if(isEmpty()){
            first = newNode;
        } else {
            last.setNext(newNode);
        }
        last = newNode;
        size++;
    }

    public void add(T item){
        insertLast(item);
    }

    public void insert(int index, T item){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Index out of bounds");
        }
        if(index == 0) {
            insertFirst(item);
            return;
        }
        if(index == size) {
            insertLast(item);
            return;
        }

        Node current = first;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
        }
        Node newNode = new Node(item, current.getNext(), current);
        current.getNext().setPrev(newNode);
        current.setNext(newNode);

        size++;
    }

    public boolean remove(T item){
        if(isEmpty()){
            return false;
        }
        if(getFirst().equals(item)){
            removeFirst();
            return true;
        }
        Node current = first;
        while(current != null && !current.getValue().equals(item)){
            current = current.getNext();
        }
        if(current == null){
            return false;
        }
        current.getNext().setPrev(current.getPrev());
        current.getPrev().setNext(current.getNext());

        size--;
        return true;
    }


    public T removeFirst() throws RuntimeException{
        T temp = getFirst();
        first = first.getNext();
        if(isEmpty()){
            last = null;
        } else {
            first.setPrev(null);
        }
        size--;
        return temp;
    }

    public T removeLast(){
        T temp = getLast();
        if(last.getPrev() != null){
            last.getPrev().setNext(null);
        } else {
            first = null;
        }
        last = last.getPrev();
        size--;
        return temp;
    }

    public T remove(int index){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Index out of bounds");
        }
        if(index == 0) {
            return removeFirst();
        }
        if(index == size) {
            return removeLast();
        }
        int temp = 1;
        Node current = first.getNext();
        while(temp < index){
            current = current.getNext();
            temp++;
        }
        current.getNext().setPrev(current.getPrev());
        current.getPrev().setNext(current.getNext());
        size--;
        return current.getValue();
    }

    public T getFirst() throws RuntimeException{
        if(isEmpty()){
            throw new RuntimeException("List is empty");
        }
        return first.getValue();
    }

    public T getLast(){
        if(isEmpty()){
            throw new RuntimeException("List is empty");
        }
        return last.getValue();
    }

    public int index(T item){
        if(isEmpty()){
            return -1;
        }
        int index = 0;
        Node current = first;
        while(current != null){
            if(current.getValue().equals(item)){
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    @Override
    public String toString() {
        Node current = first;
        StringBuilder sb = new StringBuilder("[");
        while(current != null){
            sb.append(current.getValue()).append(", ");
            current = current.getNext();
        }
        if(size > 0){
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }
}
