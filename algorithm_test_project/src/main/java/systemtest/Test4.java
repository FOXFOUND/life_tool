package systemtest;

public class Test4 {

    public void quickSort(int[] nums) {

        int left = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                left++;
            } else {
                break;
            }
        }

        int right = nums.length - 1;
        int temp = nums[left];
        while (left < right) {

            while (left < right && nums[right] <= 0) {
                right--;
            }
            nums[left] = nums[right];

            while (left < right && nums[left] > 0) {
                left++;
            }
            nums[right] = nums[left];
        }

        nums[left] = temp;

    }
}
