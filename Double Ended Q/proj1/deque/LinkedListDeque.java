package deque;

public class LinkedListDeque<T> implements Deque<T>{

    private int size;
    private Node front;
    private Node back;

    private class Node {
        private Node prev;
        private Node next;
        private T item;

        public Node(Node prev_,Node next_, T item_) {
            prev = prev_;
            next = next_;
            item = item_;

        }
    }


    @Override
    public void addFirst(T item) {

        Node el = new Node(front, front.next, item);
        front.next.prev = el;
        front.next = el;
        size++;

    }

    @Override
    public void addLast(T item) {

        Node el = new Node(back.prev, back, item);
        back.prev.next = el;
        back.prev = el;
        size++;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {

        int i = 0;
        Node temp = front.next;
        while (i < (size - 1)) {
            System.out.print(temp.item + " ");
            temp = temp.next;
            i++;
        }

        System.out.println(temp.item);


    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node removed = front.next;
        removed.next.prev = front;
        front.next = removed.next;

        size--;
        return removed.item;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        Node removed = back.prev;
        removed.prev.next = back;
        back.prev = removed.prev;

        size--;
        return removed.item;

    }

    @Override
    public T get(int index) {

        if (index > size) {
            return null;
        }

        if (index == 0) {

        }

        Node temp = front.next;
        while(index > 0) {
            temp = temp.next;
            index--;
        }

        return temp.item;
    }

    public LinkedListDeque() {
        size = 0;
        front = new Node(null,null,null);
        back = new Node(null, null,null);
        back.prev = front;
        front.next = back;
    }

    public T getRecursive(int index) {

        return recHelp(front.next, index).item;
    }

    private Node recHelp(Node pointer, int index) {

        while (index > 0) {
            return recHelp(pointer.next,index - 1);
        }

        return pointer;

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
