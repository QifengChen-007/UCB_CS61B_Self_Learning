package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove(){
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        for (int i=4; i<7; i++){
            correct.addLast(i);
            broken.addLast(i);
        }

        assertEquals(correct.size(),broken.size());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());

    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        int N = 500;

        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int correct_size = correct.size();
                int broken_size = broken.size();
                assertEquals(correct_size,broken_size);

            } else if (operationNumber == 2 && correct.size() >0){
                int correctLast = correct.getLast();
                int brokenLast = broken.getLast();
                assertEquals(correctLast,brokenLast);

            } else if (operationNumber == 3 && correct.size() >0){
                int correctLast = correct.removeLast();
                int brokenLast = broken.removeLast();
                assertEquals(correctLast,brokenLast);
            } else continue;
        }
    }


}
