package com.java.algorithm.sort.PigeonholeSort;

import java.util.Arrays;

public class PigeonholeSortCaseThree {

    public static void main(String[] args) {
        int[] arr={3,2,4,7,8,9,4,7,8,9,9,9,9,-4,9,9};
        System.out.println("排序前："+ Arrays.toString(arr));
        sort(arr);
        System.out.println("排序后："+ Arrays.toString(arr));
    }

    static void sort(int[] array) {
        // 第一步：确定数组中最大和最小元素
        int maxNum = array[0];
        int minNum = array[0];
        for (int i : array) {
            if (i > maxNum) {
                maxNum = i;
            }
            if (i < minNum) {
                minNum = i;
            }
        }

        // 第二步：创建辅助数组，注意这里是Integer，所以默认每个元素的值都是null

        // int bucketArrayLength = Math.max(maxNum - minNum + 1, array.length); // 错误的，错误原因如下:
        // 第三种情况，数组中存在重复的元素，数据元素有正有负；bucketArrayLength的做法是有问题的;
        // 比如arr={3,2,4,7,8,9,4,7,8,9,9,9,9,-4,9,9}; 也就是说，Math.max(maxNum - minNum + 1,array.length);
        // 当maxNum - minNum + 1<array.length这种情况，你取array.length显然是不行的；array.length的话，
        // 意味着你新数组里面每项将都不为空; 跟源数组长度一致；那怎么可能呢？ bucketArray中间有空元素是正常的；
        // 所以极限情况应该是bucketArrayLength = maxNum - minNum + 1 + array.length;
        // 无论怎么样将数组延长array.length；那不管怎样都不会超出；
        // 第三步：将数组中的分配到bucket数组中 这里面也有问题。不为空就distributionIndex++;
        // 理论上应该还要比较一下. 比如我之前的数组{3,2,4,7,8,9,4,7,8,9,9,9,9,-4,9,9}; 7,8,9放过之后，
        // 后面第二次放7的时候 显然就不行了，如果不比较大小，就会把第二个7放在第一个9后面;
        // 所以应该比较大小，顺序不符就得交换bucketArray[distributionIndex]跟array[i]； 这是我能看到的；

        // 正确的辅助数组的大小
        int bucketArrayLength = maxNum - minNum + 1 + array.length;
        Integer[] bucketArray = new Integer[bucketArrayLength];

        //第三步：将数组中的分配到bucket数组中
        for(int i=0;i<array.length;i++){
            int distributionIndex = array[i] - minNum;
            while(true) {
                // 如果位置没有被占,array[i]就占据
                if(bucketArray[distributionIndex] == null){
                    bucketArray[distributionIndex] = array[i];
                    break;
                }else{
                    // 此处要特别注意,因为涉及有元素占据了当前桶的位置
                    if(bucketArray[distributionIndex] == array[i]){
                        distributionIndex++;
                    } else if(bucketArray[distributionIndex] < array[i]){
                        distributionIndex++;
                    } else {
                        swap(array, i, bucketArray, distributionIndex);
                        distributionIndex++;
                    }
                }
            }
        }

        //第四步：迭代辅助数组，将不是null的元素依次拷贝到待排序数组中
        int index=-1;
        for (int i = 0; i < bucketArray.length; i++) {
            if(bucketArray[i]!=null){
                array[++index]=bucketArray[i];
            }
        }

    }

    static void swap(int[] array,int i,Integer[] bucketArray, int j) {
        int temp = bucketArray[j];
        bucketArray[j] = array[i];
        array[i] = temp;
    }

}
