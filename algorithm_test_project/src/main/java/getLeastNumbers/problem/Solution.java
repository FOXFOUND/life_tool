package getLeastNumbers.problem;

public class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {

        int[] res = new int[k];

        int n = arr.length;
        for (int i = n / 2; i >= 0; i--) {
            heapfy(arr, i, n);
        }

        int index = 0;
        for (int i = n - 1; i >= 0; i--) {

            if (index == k) {
                break;
            }

            res[index] = arr[0];
            index++;
            swap(arr, 0, i);
            heapfy(arr, 0, i);

        }

        return res;

    }

    private void heapfy(int[] arr, int current, int n) {
        int min = current;
        int left = 2 * current + 1;
        int right = 2 * current + 2;
        if (left < n && arr[left] < arr[min]) {
            min = left;
        }
        if (right < n && arr[right] < arr[min]) {
            min = right;
        }

        if (min != current) {
            swap(arr, min, current);
            heapfy(arr, min, n);
        }
    }

    private void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }
}
