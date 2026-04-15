import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Lab11_Tests {
    /*
        Complete the test case below that checks to see that threads A and B have both contributed 100 entries respectively
        to the shared ArrayList when they have both finished running.
    */
    @Test
    public void test1() {
        Lab11_Thread threadA = new Lab11_Thread("A1", 100);
        Lab11_Thread threadB = new Lab11_Thread("B1", 100);

        threadA.setData(new ArrayList<String>());

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Both threads contributed 100 entries each so 200 total
        assertEquals(200, threadA.getData().size());

    }

    /*
        Complete the test case below that checks to see if the shared ArrayList has at least 10 entries after 500ms of system time
    */
    @Test
    public void test2() {

        Lab11_Thread threadA = new Lab11_Thread("A2", 500);
        Lab11_Thread threadB = new Lab11_Thread("B2", 500);

        // Reset shared static list so other tests don't bleed 
        threadA.setData(new ArrayList<String>());

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Each thread sleeps 50ms per entry, so after 500ms each should have added ~10
        // Combined they should have at least 10 entries
        assertTrue(threadA.getData().size() >= 10);

    }

    /*
        Complete the test case below that checks to see if thread A finishes adding its 10 entries before thread B was allowed to 
        add anything to the shared ArrayList
    */
    @Test
    public void test3() {
        Lab11_Thread threadA = new Lab11_Thread("A3", 10);
        Lab11_Thread threadB = new Lab11_Thread("B3", 10);

        // Reset shared static list so other tests don't bleed in
        threadA.setData(new ArrayList<String>());

        threadA.start();

        try {
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // first 10 entries must all belong to A3
        for (int i = 0; i < 10; i++) {
            assertTrue(threadA.getData().get(i).startsWith("A3"));
        }

        threadB.start();

        try {
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // After B finishes, total should be 20
        assertEquals(20, threadA.getData().size());
    }
}
