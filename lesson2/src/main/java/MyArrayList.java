import java.util.Arrays;

public class MyArrayList<T> {
    private T[] list;
    private int size;
    private final int DEFAULT_CAPACITY = 10;
    private int capacity;

    public MyArrayList() {
        list = (T[]) new Object[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
    }

    private int index(T item){
        for(int i = 0; i < size; i++){
            if(list[i] != null && list[i].equals(item)){
                return i;
            }

        }
        return -1;
    }

    public void add(T item) {
        checkCapacity();
        list[size] = item;
        size++;
    }

    @Override
    public String toString() {

        return Arrays.stream(list)
                .limit(size)
                .toList()
                .toString();

    }

    public void add(int index, T item){
        checkIndex(index);
        checkCapacity();
        for(int j = size - 1; j > index; j--){
            list[j] = list[j - 1];
        }
        list[index] = item;
        size++;
    }

    public void remove(int index){
        checkIndex(index);
        for(int i = index; i < size; i++){
            list[i] = list[i + 1];
        }
        list[size - 1] = null;
        size--;
    }

    public boolean remove(T item){
        int index = index(item);
        if(index >= 0) {
            remove(index);
        }
        return true;
    }

    public int size() {
        return size;
    }

    public T get(int index){
        checkIndex(index);
        return list[index];
    }

    public int getCapacity() {
        return capacity;
    }

    private void checkCapacity(){
        if(size == capacity){
            capacity = (int) Math.floor(list.length * 1.5 + 1);
            T[] newList = (T[]) new Object[DEFAULT_CAPACITY];
            System.arraycopy(list, 0, newList, 0, size);
            list = newList;
        }
    }

    private void checkIndex(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
    }
}









