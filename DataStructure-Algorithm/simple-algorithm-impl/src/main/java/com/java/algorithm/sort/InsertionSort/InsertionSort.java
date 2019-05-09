package com.java.algorithm.sort.InsertionSort;

import java.io.FileReader;
import java.util.Objects;
import com.google.gson.Gson;

public class InsertionSort {

    public static void sort(int[] arr,boolean asc){
        //有序区最后一个元素位置
        //开始排序时，将有序区结束位置设为0 (开始位置总是0)，对应的无序区范围就是 [1, arr.length-1]
        int orderedLastIndex=0;
        for (int i = orderedLastIndex+1; i < arr.length; i++) { //迭代无序区中的每一个元素，依次插入有序区中
            int temp=arr[i];//记录无序区中的第一个元素值
            int insertIndex=i;//在有序区中插入的索引的位置，刚开始就设置为自己的位置
            for (int j = orderedLastIndex; j >= 0; j--) { //从有序区从后往前开始比较
                if(asc){
                    if(arr[j]>temp){//升序，有序区中比当前无序区中元素大的都右移一个位置
                        arr[j+1]=arr[j];
                        insertIndex--;//有序区每移动一次，将插入位置-1
                    }else{
                        break;//有序区当前位置元素<=无序区第一个元素，那么之前的元素都会<=，不需要继续比较
                    }
                }else{//升序，有序区中比当前无序区中元素小的都右移一个位置
                    if(arr[j]<temp){
                        arr[j+1]=arr[j];
                        insertIndex--;
                    }else{
                        break;
                    }
                }
            }
            arr[insertIndex]=temp;
            orderedLastIndex++;
        }
    }

	public static void main(String[] args) {
		ClassLoader classLoader = InsertionSort.class.getClassLoader();
		String filePath = classLoader.getResource("insertion-sort/input.json").getFile();
		try {
			InsertionSortInput[] inputs = new Gson().fromJson(new FileReader(filePath), InsertionSortInput[].class);
			for (InsertionSortInput input : inputs) {
				int[] array = new int[input.array.length];
				System.arraycopy(input.array, 0, array, 0, array.length);
				System.out.println("original => " + new Gson().toJson(array));
				sort(array, true);
				System.out.println("sorted => " + new Gson().toJson(array));
				if (!Objects.deepEquals(array, input.result)) {
					throw new Exception("failed. expected = " + new Gson().toJson(input.result) + ", actual = " 
							+ new Gson().toJson(array));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static void sort(double[] arr,boolean asc){
        //有序区最后一个元素位置
        //开始排序时，将有序区结束位置设为0 (开始位置总是0)，对应的无序区范围就是 [1, arr.length-1]
        int orderedLastIndex=0;
        for (int i = orderedLastIndex+1; i < arr.length; i++) { //迭代无序区中的每一个元素，依次插入有序区中
            double temp=arr[i];//记录无序区中的第一个元素值
            int insertIndex=i;//在有序区中插入的索引的位置，刚开始就设置为自己的位置
            for (int j = orderedLastIndex; j >= 0; j--) { //从有序区从后往前开始比较
                if(asc){
                    if(arr[j]>temp){//升序，有序区中比当前无序区中元素大的都右移一个位置
                        arr[j+1]=arr[j];
                        insertIndex--;//有序区每移动一次，将插入位置-1
                    }else{
                        break;//有序区当前位置元素<=无序区第一个元素，那么之前的元素都会<=，不需要继续比较
                    }
                }else{//升序，有序区中比当前无序区中元素小的都右移一个位置
                    if(arr[j]<temp){
                        arr[j+1]=arr[j];
                        insertIndex--;
                    }else{
                        break;
                    }
                }
            }
            arr[insertIndex]=temp;
            orderedLastIndex++;
        }
    }
}

class InsertionSortInput {
	int[] array;
	int[] result;
}