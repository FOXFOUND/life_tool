package minimumTime.problem;

public class Solution {
    public long minimumTime(int[] time, int totalTrips) {

        int start = 1;
        int[] finished = new int[time.length];

        while (true) {
            int sum = 0;
            for (int i = 0; i < time.length; i++) {
                if (start %  time[i] == 0) {
                    finished[i] += 1;
                }

            }
            for (int i = 0; i < finished.length; i++) {
                sum += finished[i];
            }
            if (sum >= totalTrips) {
                return start;
            } else {
                start++;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(2%1);
    }
}
