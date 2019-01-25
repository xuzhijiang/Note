冒泡排序

冒泡排序是最简单的排序算法，通过重复交换相邻元素（如果它们的顺序错误）来工作。

First Pass(第一趟把最大的元素放到了数组中的最后一个位置):
( 5 1 4 2 8 ) –> ( 1 5 4 2 8 ), Here, algorithm compares
the first two elements, and swaps since 5 > 1.
( 1 5 4 2 8 ) –>  ( 1 4 5 2 8 ), Swap since 5 > 4
( 1 4 5 2 8 ) –>  ( 1 4 2 5 8 ), Swap since 5 > 2
( 1 4 2 5 8 ) –> ( 1 4 2 5 8 ), Now, since these elements are
already in order (8 > 5), algorithm does not swap them.

Second Pass(第二趟把第二大的元素放到了数组中的倒数第二个位置):
( 1 4 2 5 8 ) –> ( 1 4 2 5 8 )
( 1 4 2 5 8 ) –> ( 1 2 4 5 8 ), Swap since 4 > 2
( 1 2 4 5 8 ) –> ( 1 2 4 5 8 )
( 1 2 4 5 8 ) –>  ( 1 2 4 5 8 )
现在，数组已经排好了序，但我们的算法不知道它是否完成了排序，因此还会继续的
比较。

Third Pass:
( 1 2 4 5 8 ) –> ( 1 2 4 5 8 )
( 1 2 4 5 8 ) –> ( 1 2 4 5 8 )
( 1 2 4 5 8 ) –> ( 1 2 4 5 8 )
( 1 2 4 5 8 ) –> ( 1 2 4 5 8 )

-----------------------------------------------------------------

Worst and Average Case Time Complexity: O(n*n).
Worst case occurs when array is reverse sorted.

Best Case Time Complexity: O(n). Best case occurs when array is already sorted.

Boundary Cases: Bubble sort takes minimum time (Order of n)
when elements are already sorted.

Stable: Yes

冒泡排序的最优时间复杂度为：O(n)
最坏时间复杂度为：O(n2)
平均时间复杂度为：O(n2)

-------------------------------------------------------------------
冒到第一位，冒到第二位