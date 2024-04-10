package arr.move.problem;

public class Solution {

    public int getNumer(int[] arr, int k) {

        int maxIndex = 0;

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if ((i + 1) < n && arr[i] > arr[i + 1]) {
                maxIndex = i;
                break;
            }
        }


        if (arr[0] <= k && k <= arr[maxIndex]) {

            int left = 0;
            int right = maxIndex;

            while (left <= right) {
                int middle = (left + right) / 2;

                if (arr[middle] > k) {
                    right = middle;
                } else if (arr[middle] == k) {
                    return middle;
                } else {
                    left = middle + 1;
                }
            }
        } else {
            int left = maxIndex + 1;
            int right = n - 1;
            while (left <= right) {
                int middle = (left + right) / 2;

                if (arr[middle] > k) {
                    right = middle;
                } else if (arr[middle] == k) {
                    return middle;
                } else {
                    left = middle + 1;
                }
            }

        }

        return -1;

    }

    public static void main(String[] args) {

        Solution solution = new Solution();
        int[] arr = new int[]{4, 5, 0, 1, 2, 3};
        //int position = solution.getNumer(arr, 5);
        int position = solution.getNumer(arr, 2);
        System.out.println(position);

    }
}
