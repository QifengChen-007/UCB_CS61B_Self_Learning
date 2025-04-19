package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }



    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCount = new AList<>();

        int M = 10000;
        int count = 8;
        int basesize = 1000;

        for (int i=0; i<count; i++){
            SLList<Integer> test = new SLList<>();
            Ns.addLast(basesize);

            for (int j=0; j<count; j++){
                test.addLast(0);
            }

            Stopwatch sw = new Stopwatch();
            for (int k=0; k<M; k++){
                test.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            opCount.addLast(basesize);
            basesize = basesize * 2;
        }
        printTimingTable(Ns, times, opCount);

    }
}
