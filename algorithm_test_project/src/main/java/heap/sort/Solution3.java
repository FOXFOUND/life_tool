package heap.sort;

/**
 * 该算法首先构建一个大根堆，即满足父节点大于等于左右孩子节点的完全二叉树。从最后一个非叶子节点开始，将其与它的左右孩子结点进行比较，找到三者之间的最大值，并将最大值作为父节点。重复这个过程直到树的所有节点都满足大根堆的要求。
 *
 * 接着依次将堆顶元素与末尾元素交换，并重新调整大根堆使得除已经排序的元素之外的子数组仍然满足大根堆的要求，重复直到整个数组有序。
 */
public class Solution3 {
    public static void heapSort(int[] nums) {
        int n = nums.length;
        // 构建大根堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }
        // 依次将堆顶元素与末尾元素交换，并重新调整大根堆
        for (int i = n - 1; i >= 0; i--) {
            swap(nums, 0, i);
            heapify(nums, i, 0);
        }
    }

    private static void heapify(int[] nums, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        // 找到当前结点、左孩子结点、右孩子结点中的最大值
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }
        // 如果最大值不是当前结点，则将最大值与当前结点交换，并继续调整下面的堆
        if (largest != i) {
            swap(nums, i, largest);
            heapify(nums, n, largest);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
