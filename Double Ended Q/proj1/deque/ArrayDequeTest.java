package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<Integer>();

    @Test
    public void testAddRemove() {
        Deque<Integer> ad = new ArrayDeque<Integer>();

        ad.addFirst(1);
        ad.addLast(2);
        ad.addLast(3);
        ad.addFirst(0);

        assertTrue("expecting first value to be true", 0 == ad.get(0));

        ad.addLast(4);
        ad.addLast(5);
        ad.addLast(6);
        ad.addLast(7);
        ad.addLast(8);

        ad.removeFirst();
        ad.removeFirst();
        assertTrue("checking if remove works", ad.removeFirst() == 2);
        ad.removeLast();
        ad.removeLast();
        assertTrue("checking if remove works", ad.removeLast() == 6);
    }

    @Test
    public void addIsEmptySizeTest() {
        Deque<Integer> ad = new ArrayDeque<Integer>();

        assertTrue("testing if is empty works", ad.isEmpty() == true);

        ad.addFirst(1);
        assertTrue("testing if is empty works again", ad.isEmpty() == false);
        ad.removeLast();

        assertTrue("testing if is empty works again", ad.isEmpty() == true);

    }

    @Test
    public void removeEmptyTest() {
        Deque<Integer> ad = new ArrayDeque<Integer>();
        ad.removeFirst();

        assertTrue("This is true", ad.size()==0);

        ad.addFirst(1);
        ad.removeFirst();
        ad.removeLast();
        ad.removeFirst();

        assertTrue("Size should be zero", ad.size()==0);

    }

    @Test
    public void multipleParamsTest() {

        Deque<String> ad = new ArrayDeque<String>();

        ad.addFirst("a");
        ad.addFirst("b");
        ad.addFirst("c");

        assertTrue("testing if strings can be recovered", ad.get(1) == "b" );
    }

    @Test
    public void expandTest() {

        Deque<Integer> ad = new ArrayDeque<Integer>();

        int i = 0;
        while (i < 16) {
            ad.addLast(i);
            i++;
        }

        assertTrue("testing if size expanding works", ad.size() == 16);

        i = 0;
        while (i < 12) {
            ad.removeLast();
            i++;
        }

        ad.removeLast();

        assertTrue("checking if size of array is 8", ad.size() == 3);




    }
    @Test
    public void equalityTest(){
        Deque<Integer> ad = new ArrayDeque<Integer>();
        Deque<Integer> bd = new ArrayDeque<Integer>();

        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        bd.addLast(1);
        bd.addLast(2);
        bd.addLast(3);

        assertTrue("These are equal", ad.equals(bd));
        assertTrue("These are equal", ad.equals(ad));

        bd.addLast(4);
        assertFalse("These are equal", ad.equals(bd));


    }




}
