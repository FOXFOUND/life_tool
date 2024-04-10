package quick.sort;

import java.util.Arrays;

public class Solution2 {

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pre = quickSortPartion(arr, left, right);
            quickSort(arr, left, pre - 1);
            quickSort(arr, pre + 1, right);

        }

    }

    private static int quickSortPartion(int[] arr, int left, int right) {

        int middle = arr[left];
        while (left < right) {
            while (arr[right] > middle && left < right) {
                right--;
            }
            arr[left] = arr[right];
            while (arr[left] <= middle && left < right) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = middle;
        return left;

    }
}
