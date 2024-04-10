package quick.sort;

import java.util.*;

public class QuickSortNonRecursive {

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2};
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        int left = 0;
        int right = arr.length - 1;
        stack.push(left);
        stack.push(right);

        while (!stack.isEmpty()) {
            right = stack.pop();
            left = stack.pop();
            if (left >= right) {
                continue;
            }
            int pivot = partition(arr, left, right);
            stack.push(left);
            stack.push(pivot - 1);
            stack.push(pivot + 1);
            stack.push(right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot) {
                right--;
            }
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }
}
