package deque;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. Please do not import java.util.Deque here!*/

    public static Deque<Integer> lld = new LinkedListDeque<Integer>();
    @Test
    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        lld = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
        lld.addFirst(0);
        assertFalse("lld1 should now contain 1 item", lld.isEmpty());
        lld = new LinkedListDeque<Integer>(); //Assigns lld equal to a new, clean LinkedListDeque!

    }

    /** Adds an item, removes an item, and ensures that dll is empty afterwards. */
    @Test
    public void addRemoveTest() {

        lld = new LinkedListDeque<Integer>();
        lld.addLast(2);
        lld.addFirst(1);

        assertTrue("First value in deque should be 1", lld.get(0) == 1);

        lld.addFirst(0);
        lld.addLast(3);

        assertEquals(4,lld.size());
        assertTrue("First value in deque should be 0", lld.get(0) == 0);
        assertTrue("Last value in deque should be 3", lld.get(3) == 3);

        lld.printDeque();


    }
    /** Make sure that removing from an empty LinkedListDeque does nothing */
    @Test
    public void removeEmptyTest() {

        lld = new LinkedListDeque<Integer>();

        assertTrue("checking if removing nothing returns null", lld.removeFirst() == null);
        assertTrue("checking if size is 0", lld.size() == 0);

    }
    /** Make sure your LinkedListDeque also works on non-Integer types */
    @Test
    public void multipleParamsTest() {

        Deque<String> lld = new LinkedListDeque<String>();

        lld.addFirst("a");
        lld.addLast("b");
        lld.addLast("c");
        lld.addLast("d");

        assertTrue("returns c at correct index", lld.get(2) == "c");
        assertTrue("returns c at correct index", lld.get(2) == "c");

    }
    /** Make sure that removing from an empty LinkedListDeque returns null */
    @Test
    public void emptyNullReturn() {

        lld = new LinkedListDeque<Integer>();

        assertTrue(lld.removeFirst() == null);
        assertTrue(lld.removeLast() == null);

        lld.addFirst(1);
        lld.addLast(3);
        lld.removeLast();
        lld.removeLast();

        assertTrue(lld.removeLast() == null);


    }

    @Test
    public void reallyBigTest() {
        lld = new LinkedListDeque<Integer>();
        int i = 0;
        while(i < 1000000) {
            lld.addLast(i);
            i++;
        }

        assertTrue("checking if this works",50 == lld.get(50));
        assertTrue("checking if this works",500000 == lld.get(500000));

    }

}
