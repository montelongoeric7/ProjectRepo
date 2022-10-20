package deque;

import java.sql.Array;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private int nextFirst;
    private T[] items;
    private int nextLast;


    private void resize(String str) {

        if (str == "smaller") {
            if (((size) < items.length / 4) && items.length >= 16) {

                T[] newItems = (T[]) new Object[items.length / 2];
                int i = 0;
                while (i < size) {
                    newItems[i] = items[(nextFirst + i + 1) % items.length];
                    i++;
                }
                items = newItems;
                nextFirst = items.length - 1;
                nextLast = size;
                return;

            }
            return;

        }

        if (str == "larger") {
            if ((size + 1) > items.length) {

                T[] newItems = (T[]) new Object[items.length * 2];
                int i = 0;
                while (i < size) {
                    newItems[i] = items[(nextFirst + i + 1) % items.length];
                    i++;
                }

                items = newItems;
                nextFirst = items.length - 1;
                nextLast = size;
                return;
            }
            return;
        }

    }


    @Override
    public void addFirst(T item) {
        resize("larger");

        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    @Override
    public void addLast(T item) {
        resize("larger");

        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int i = 0;
        while (i < (size - 1)) {
            System.out.print(items[(nextFirst + 1 + i) % (items.length)] + " ");
            i++;
        }
        System.out.println(items[(nextFirst + 1 + i) % (items.length)]);
    }

    @Override
    public T removeFirst() {

        T removed = items[(nextFirst + 1) % (items.length)];

        if (removed == null || (size == 0))  {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        size--;
        resize("smaller");

        return removed;
    }

    @Override
    public T removeLast() {
        T removed = items[(nextLast - 1 + items.length) % items.length];

        if (removed == null || (size == 0)) {
            return null;
        }

        nextLast = (nextLast - 1 + items.length) % items.length;
        size--;
        resize("smaller");

        return removed;
    }

    @Override
    public T get(int index) {
        return items[(nextFirst + 1 + index) % items.length];
    }

    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        items = (T[]) new Object[8];
        nextLast = 1;
    }

    public boolean equals(Object o) {
        if ((o == null) || !(o instanceof Deque)) {
            return false;
        }

        Deque oo = (Deque) o;

        if (oo.size() != this.size()) {
            return false;
        }

        int i = 0;
        while(i < size) {
            if (!this.get(i).equals(oo.get(i))) {
                return false;
            }
            i++;
        }

        return true;
    }


}
