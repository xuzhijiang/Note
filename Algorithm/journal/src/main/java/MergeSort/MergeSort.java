package MergeSort;

import java.util.Arrays;

/*
 * 时间复杂度为O(nlog₂n),这是该算法中最好、最坏和平均的时间性能。
 * 归并排序的时间复杂度与插入排序相比，已经降低了很多，
 * 这一点在数组的输入规模较大时将会非常明显，因为log函数的增加速度将远远低于n的增加速度。
 */
public class MergeSort {
    
	/**
	 * 把已经排序好的2个数组合并
	 * @param array
	 * @param low
	 * @param mid
	 * @param high
	 */
    public static void merge(int[] array, int low, int mid, int high) {
    	// 例如：把两个已排序好的子数组{3, 27, 38, 43} 和 {9, 10, 82}
    	
    	// {3, 27, 38, 43,   9, 10, 82}
    	//  0   1   2  3     4   5   6
        int[] temp = new int[high - low + 1];// high - low + 1 等于元素总数
        int i = low;// 左指针(左边数组的第一个元素)
        int j = mid + 1;// 右指针(右边数组的第一个元素)
        int k = 0;
        
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (array[i] < array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        
        // 当某一个数组的元素消耗完时，把未消耗完的数组里面的元素拷贝到临时数组
        
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = array[j++];
        }
        
        // 把临时数组中的元素 覆盖源数组array中的元素
        for (int t = 0; t < temp.length; t++) {
            array[t + low] = temp[t];
            // 使用System.arraycopy函数进行数组复制，
            // 这种直接复制内存区域的方式，将会比循环赋值的方式速度更快
        }
    }

    /**
     * 递归的分割
     * @param a
     * @param low
     * @param high
     */
    public static void separate(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        // 当数组大小为1时，则认为该数组为已经为排好序的数组
        // 因此在分割方法中，需要high > low时，才需要进一步分割，这也是递归的终止条件
        if (low < high) {
            // 左边
        	separate(a, low, mid);
            // 右边
        	separate(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
            System.out.println(Arrays.toString(a));
        }
    }

    public static void main(String[] args) {
        int a[] = { 51, 46, 20, 18, 65, 97, 82, 30, 77, 50 };
        separate(a, 0, a.length - 1);
        System.out.println("排序结果：" + Arrays.toString(a));
    }
    
}