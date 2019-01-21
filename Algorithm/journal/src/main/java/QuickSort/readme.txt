QuickSort

与Merge Sort类似，QuickSort是一种Divide and Conquer算法。
它挑选一个元素作为哨兵，并围绕这个哨兵分割数组。 有许多方式选择哨兵:

1. 始终选择第一个元素作为哨兵
2. 始终选择最后一个元素为哨兵（在下面实现）
3. 选择一个随机元素作为哨兵
4. 选择中位数作为哨兵

quickSort中的关键是partition()。 分组的目标是，选择数组的一个元素x作为哨兵，
将x放在排序数组中的正确位置，并将所有较小的元素（小于x）放在x之前，并将所有更大的元素
（大于x）放在x之后 。 所有这些都应该在线性时间内完成。

							 {10, 80, 30, 90, 40, 50, 70}
						   Partition around 70 (Last Element)
						   /                      \
						  /                        \
						 /							\
				 {10, 30, 40, 50}					{90, 80}
Partition around     /     \						/    \
	50			    /       \					   /	  \Partition around 80
				   /         \          		 {}		   {90}
			{10, 30, 40}      {}
			     /  \
Partition       /	 \		    
around 40      /      \
             {10, 30}  {}
               /  \
              /    \
             /      \
           {10}     {}


Partition(分组)算法

可以有很多方法来进行分组，逻辑很简单，我们从最左边的元素开始，跟踪小于（或等于）索引为i的元素。 
遍历时，如果我们找到一个较小的元素，我们用arr[i]交换当前元素。 否则我们忽略当前元素。

----------------------------------------------------------------

Illustration of partition() :

arr[] = {10, 80, 30, 90, 40, 50, 70}
Indexes:  0   1   2   3   4   5   6 

low = 0, high =  6, pivot = arr[h] = 70
Initialize index of smaller element, i = -1

从索引为j=low 到 j=high-1，遍历这个区间的元素
Traverse elements from j = low to high-1
j = 0 : Since arr[j] <= pivot, do i++ and swap(arr[i], arr[j])
i = 0
arr[] = {10, 80, 30, 90, 40, 50, 70} // No change as i and j are same
                                     
j = 1 : Since arr[j] > pivot, do nothing
// No change in i and arr[] (索引i和arr数组都没有变化)

j = 2 : Since arr[j] <= pivot, do i++ and swap(arr[i], arr[j])
i = 1
arr[] = {10, 30, 80, 90, 40, 50, 70} // We swap 80 and 30 

j = 3 : Since arr[j] > pivot, do nothing
// No change in i and arr[]

j = 4 : Since arr[j] <= pivot, do i++ and swap(arr[i], arr[j])
i = 2
arr[] = {10, 30, 40, 90, 80, 50, 70} // 80 and 40 Swapped
j = 5 : Since arr[j] <= pivot, do i++ and swap arr[i] with arr[j] 
i = 3 
arr[] = {10, 30, 40, 50, 80, 90, 70} // 90 and 50 Swapped 

We come out of loop because j is now equal to high-1.
Finally we place pivot at correct position by swapping
arr[i+1] and arr[high] (or pivot) 
arr[] = {10, 30, 40, 50, 70, 90, 80} // 80 and 70 Swapped 

Now 70 is at its correct place. All elements smaller than
70 are before it and all elements greater than 70 are after
it.

--------------------------------------------------------------------------

arr[] = {110, 80, 30, 90, 40, 50, 70}
Indexes:  0   1   2   3   4   5   6 

low = 0, high =  6, pivot = arr[h] = 70
Initialize index of smaller element, i = -1

从索引为j=low 到 j=high-1，遍历这个区间的元素
Traverse elements from j = low to high-1
j = 0 : Since arr[j] > pivot, do nothing
// No change in i and arr[]
                                     
j = 1 : Since arr[j] > pivot, do nothing
// No change in i and arr[] (索引i和arr数组都没有变化)

j = 2 : Since arr[j] <= pivot, do i++ and swap(arr[i], arr[j])
i = 0
arr[] = {30, 80, 110, 90, 40, 50, 70} // We swap 110 and 30 

j = 3 : Since arr[j] > pivot, do nothing
// No change in i and arr[]

j = 4 : Since arr[j] <= pivot, do i++ and swap(arr[i], arr[j])
i = 1
arr[] = {30, 40, 110, 90, 80, 50, 70} // 80 and 40 Swapped

j = 5 : Since arr[j] <= pivot, do i++ and swap arr[i] with arr[j] 
i = 2
arr[] = {30, 40, 50, 90, 80, 110, 70} // 110 and 50 Swapped 

We come out of loop because j is now equal to high-1.
Finally we place pivot at correct position by swapping
arr[i+1] and arr[high] (or pivot) 
arr[] = {30, 40, 50, 70, 80, 110, 90} // 90 and 70 Swapped 

Now 70 is at its correct place. All elements smaller than
70 are before it and all elements greater than 70 are after
it.

-------------------------------------------------------------------

// 时间复杂度:
// 最好: O(n), 当前划分均匀
// 平均: O(nlogn)
// 最差O(n^2)

// 空间复杂度:
// 最好: O(logn)        