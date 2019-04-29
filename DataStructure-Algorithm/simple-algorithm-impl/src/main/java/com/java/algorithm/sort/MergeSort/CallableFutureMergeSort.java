package com.java.algorithm.sort.MergeSort;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CallableFutureMergeSort implements Callable<Boolean> {

    private ExecutorService threadPool;
    private Future<Boolean> loadResult0;
    private Future<Boolean> loadResult1;
    private int[] list;
    private int first;
    private int last;

    public CallableFutureMergeSort(int[] list, ExecutorService threadPool) {
        this.list = list;
        this.threadPool = threadPool;
        first = 0;
        last = list.length - 1;
    }

    public CallableFutureMergeSort(int[] list, int first, int last,
                                   ExecutorService threadPool) {
        this.list = list;
        this.first = first;
        this.last = last;
        this.threadPool = threadPool;
    }

    public static void BubbleSort(int[] list, int first, int last) {
        for (int i = first; i <= last; i++) {
            for (int j = i + 1; j <= last; j++) {
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

    public static void MergeArray(int[] list, int first, int mid, int last) {
        int i = first;
        int j = mid + 1;
        int m = mid;
        int n = last;
        ArrayList<Integer> tmp = new ArrayList<>(
                last - first + 1);
        while (i <= m && j <= n) {
            if (list[i] <= list[j]) {
                tmp.add(list[i++]);
            } else {
                tmp.add(list[j++]);
            }
        }
        while (i <= m) {
            tmp.add(list[i++]);
        }
        while (j <= n) {
            tmp.add(list[j++]);
        }
        for (int index = 0; index < tmp.size(); index++) {
            list[first + index] = tmp.get(index);
        }
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Boolean call() throws Exception {
        if (last - first > 5) {
            int mid = (first + last) / 2;
            loadResult0 = threadPool.submit(
                    new CallableFutureMergeSort(list, first, mid, threadPool)
            );
            loadResult1 = threadPool.submit(
                    new CallableFutureMergeSort(list, mid + 1, last, threadPool)
            );
            try {
                if (loadResult0.get() && loadResult1.get()) {
                    MergeArray(list, first, mid, last);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            BubbleSort(list, first, last);
        }
        return true;
    }

}