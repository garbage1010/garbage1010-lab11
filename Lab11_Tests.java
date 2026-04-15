import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Lab11_Tests {

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

        // Count how many entries belong to each thread
        int countA = 0, countB = 0;
        for (String s : threadA.getData()) {
            if (s.startsWith("A1")) countA++;
            if (s.startsWith("B1")) countB++;
        }
        assertEquals(100, countA);
        assertEquals(100, countB);
    }

    @Test
    public void test2() {
        Lab11_Thread threadA = new Lab11_Thread("A2", 500);
        Lab11_Thread threadB = new Lab11_Thread("B2", 500);

        threadA.setData(new ArrayList<String>());

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Each thread sleeps 50ms per entry, so after 500ms ~10 entries per thread
        assertTrue(threadA.getData().size() >= 10);
    }

    @Test
    public void test3() {
        Lab11_Thread threadA = new Lab11_Thread("A3", 10);
        Lab11_Thread threadB = new Lab11_Thread("B3", 10);

        threadA.setData(new ArrayList<String>());

        threadA.start();

        try {
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // After A joins, all 10 entries so far must belong to A3
        assertEquals(10, threadA.getData().size());
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