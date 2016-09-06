import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Sort {

	/**
	 * 通过与相邻元素的比较和交换来把小的数交换到最前面。这个过程类似于水泡向上升一样
	 * 
	 * @param nums
	 */
	public void bubbleSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		for (int i = 0; i < nums.length; i++) {
			for (int j = nums.length - 1; j > i; j--) {
				if (nums[j] < nums[j - 1]) {
					swap(nums, j, j - 1);
				}
			}
		}
		System.out.println(Arrays.toString(nums));
	}

	/**
	 * 选择排序是通过对整体的选择,在一次排序后把最小的元素放到最前面
	 * 
	 * @param nums
	 */
	public void selectSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		for (int i = 0; i < nums.length; i++) {
			int minIndex = i;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < nums[minIndex])
					minIndex = j;
			}
			if (minIndex != i)
				swap(nums, i, minIndex);
		}
		System.out.println(Arrays.toString(nums));
	}

	/**
	 * 通过比较找到合适的位置插入元素来达到排序的目的的,每次都是对已经排好序的段进行调整
	 * 
	 * @param nums
	 */
	public void insertSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		for (int i = 0; i < nums.length; i++) {
			for (int j = i; j > 0 && (nums[j] < nums[j - 1]); j--) {
				swap(nums, j, j - 1);
			}
		}
		System.out.println(Arrays.toString(nums));
	}

	/**
	 * 快速排序
	 * 
	 * @param nums
	 */
	public void quickSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		quickSortSub(nums, 0, nums.length - 1);
		System.out.println(Arrays.toString(nums));
	}

	public void quickSortSub(int[] nums, int left, int right) {
		if (left >= right)
			return;
		int pivotPos = partition(nums, left, right);
		quickSortSub(nums, left, pivotPos - 1);
		quickSortSub(nums, pivotPos + 1, right);
	}

	public int partition(int[] nums, int left, int right) {
		int pivotKey = nums[left];
		int pivotIndex = left;
		while (left < right) {
			while (left < right && nums[right] >= pivotKey)
				right--;
			while (left < right && nums[left] <= pivotKey)
				left++;
			swap(nums, left, right);
		}
		swap(nums, pivotIndex, right);
		return right;
	}

	/**
	 * 縮小增量排序法，在不相邻的记录之间进行比较和交换。利用插入排序的最佳时间代价特性。
	 * 将序列分成子序列，然后分别对子序列进行排序，增量序列一般为(2^k,2^k-1,...2,1)
	 * 
	 * @param nums
	 */
	public void shellSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		int d = nums.length / 2;
		while (d >= 1) {
			shellInsert(nums, d);
			d = d / 2;
		}
		System.out.println(Arrays.toString(nums));
	}

	public void shellInsert(int[] nums, int d) {
		for (int i = d; i < nums.length; i++) {
			for (int j = i; j > 0 && nums[j] < nums[j - d]; j -= d) {
				swap(nums, j, j - d);
			}
		}
	}

	/**
	 * 先递归划分子问题，然后合并结果。
	 * 把待排序列看成由两个有序的子序列，然后合并两个子序列，然后把子序列看成由两个有序序列。。。。。倒着来看，其实就是先两两合并
	 * ，然后四四合并。。。最终形成有序序列。空间复杂度为O(n)，时间复杂度为O(nlogn)。
	 * 
	 * @param nums
	 */
	public void mergeSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		mSort(nums, 0, nums.length - 1);
		System.out.println(Arrays.toString(nums));
	}

	public void mSort(int[] nums, int left, int right) {
		if (left >= right)
			return;
		int mid = left + (right - left) / 2;
		mSort(nums, left, mid);// 递归排序左边
		mSort(nums, mid + 1, right);// 递归排序右边
		merge(nums, left, mid, right);// 合并
	}

	public void merge(int[] nums, int left, int mid, int right) {
		// [left, mid] [mid+1, right]
		int[] tmp = new int[right - left + 1];
		int l = left;
		int r = mid + 1;
		int k = 0;
		while (l <= mid && r <= right) {
			if (nums[l] <= nums[r])
				tmp[k++] = nums[l++];
			else
				tmp[k++] = nums[r++];
		}
		while (l <= mid) {
			tmp[k++] = nums[l++];
		}
		while (r <= right) {
			tmp[k++] = nums[r++];
		}
		for (int i = 0; i < tmp.length; i++)
			nums[left + i] = tmp[i];
	}

	/**
	 * 堆排序
	 * 
	 * @param nums
	 */
	public void heapSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		// 建立最大堆
		for (int i = nums.length / 2; i >= 0; i--) {
			heapAdjust(nums, i, nums.length - 1);
		}
		for (int i = nums.length - 1; i >= 0; i--) {
			swap(nums, 0, i);
			heapAdjust(nums, 0, i - 1);
		}
		System.out.println(Arrays.toString(nums));
	}

	public void heapAdjust(int[] nums, int l, int r) {
		int tmp = nums[l];
		for (int i = 2 * l + 1; i <= r; i *= 2) {
			// 左右孩子的节点分别为2*i+1,2*i+2

			// //选择出左右孩子较小的下标
			if (i < r && nums[i] < nums[i + 1])
				i++;

			if (tmp >= nums[i]) {
				break; // 已经为大顶堆，=保持稳定性。
			}

			nums[l] = nums[i]; // 将子节点上移
			l = i; // 下一轮筛选
		}
		nums[l] = tmp;// 插入正确的位置
	}

	/**
	 * (1) 映射函数f(k)能够将N个数据平均的分配到M个桶中，这样每个桶就有[N/M]个数据量。
	 * 
	 * 　　(2)
	 * 尽量的增大桶的数量。极限情况下每个桶只能得到一个数据，这样就完全避开了桶内数据的“比较”排序操作。当然，做到这一点很不容易，数据量巨大的情况下
	 * ，f(k)函数会使得桶集合的数量巨大，空间浪费严重。这就是一个时间代价和空间代价的权衡问题了。
	 * 
	 * 对于N个待排数据，M个桶，平均每个桶[N/M]个数据的桶排序平均时间复杂度为：
	 * 
	 * O(N)+O(M*(N/M)*log(N/M))=O(N+N*(logN-logM))=O(N+N*logN-N*logM) 桶排序
	 * 
	 * @param nums
	 */
	public void bucketSort(int[] nums) {
		if (null == nums)
			return;
		System.out.println(Arrays.toString(nums));
		
		List<List<Integer>> buckets = new ArrayList<List<Integer>>(); // 桶的索引
		for (int i = 0; i < 10; i++) {
			buckets.add(new LinkedList<Integer>());// 用链表比较合适
		}
		for (int i = 0; i < nums.length; i++)
			buckets.get(f(nums[i])).add(nums[i]);
		// 对每个桶进行排序
		for (int i = 0; i < buckets.size(); i++) {
			if (!buckets.get(i).isEmpty()) {
				Collections.sort(buckets.get(i)); // 对每个桶进行快排
			}
		}
		// 还原排好序的数组
		int k = 0;
		for (List<Integer> bucket : buckets) {
			for (int ele : bucket)
				nums[k++] = ele;
		}
		System.out.println(Arrays.toString(nums));
	}

	/**
	 * 映射函数
	 * 
	 * @param x
	 * @return
	 */
	public static int f(int x) {
		return x / 10;
	}

	public void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Sort sort = new Sort();
		int[] num = { 5, 3, 8, 4, 6, 9 };
		sort.bucketSort(num);

	}

}
