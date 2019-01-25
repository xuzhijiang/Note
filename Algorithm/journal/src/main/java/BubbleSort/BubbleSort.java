package BubbleSort;

// Optimized java implementation of Bubble sort
public class BubbleSort {

    // An unoptimized version of Bubble sort
    static void bubbleSort(int[] list) {
        for (int i = 0; i < list.length; i++) {
            for (int j = i + 1; j < list.length; j++) {
                if (list[i] > list[j]) {
                    swap(list, i, j);
                }
            }
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    // An optimized version of Bubble Sort
    static void bubbleSort(int[] arr, int n) {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

    // 在归并排序中用到的是冒泡排序的修改版，多了两个参数first和last，
    // 只对数组中索引从first到last的元素进行冒泡排序
    public static void BubbleSort(int[] list, int first, int last) {
        for (int i = first; i <= last; i++) {
            for (int j = i + 1; j <= last; j++) {
                if (list[i] > list[j]) {
                    swap(list, i, j);
                }
            }
        }
    }

    // Function to print an array
    static void printArray(int[] arr, int size) {
        int i;
        for (i = 0; i < size; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver program
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int n = arr.length;
        bubbleSort(arr, n);
        System.out.println("Sorted array: ");
        printArray(arr, n);
    }
}
