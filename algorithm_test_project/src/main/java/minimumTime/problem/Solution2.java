package minimumTime.problem;

public class Solution2 {
    public long minimumTime(int[] time, int totalTrips) {

        int start = 1;
        int[] finished = new int[time.length];
        int sum = 0;
        while (true) {

            for (int i = 0; i < time.length; i++) {
                if (start %  time[i] == 0) {
                    sum += 1;
                }

            }
            if (sum >= totalTrips) {
                return start;
            } else {
                start++;
            }
        }
    }
}
