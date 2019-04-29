插入排序简述:

从i=1 to i=n-1 循环,(也就是从数组的第二个元素开始遍历到最后一个元素)
，将每次挑选的元素插入已经排序好的数组中: arr[0...i-1]

1, It is very efficient for small data sets.
2, It is much less efficient on large lists than other sort algorithms.
插入排序(Insertion Sort）算法是一个对少量元素进行排序的有效算法。
时间复杂度: O(n*2)

是否稳定: 是

用途：当元素数量很少时使用插入排序。
当输入数组几乎排序时，只有少数元素在完整的大数组中放错位置时，它也很有用。
It can also be useful when input com.java.datastructure.array is almost sorted,
----------------------------------------------------------------


一个例子:

{28, 0, 28, 27, 1, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9

i = 1, j 从 i -> 0 遍历:
j = 1, j > 0,
因为array[j] < com.java.datastructure.array[j-1], 所以交换28和0
(也就是后面的元素小于前面的元素,交换后，就可以保证前面的元素小于后面的元素了):

{0, 28, 28, 27, 1, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9

do j--, j = 0, 所以退出内层for循环.


do i++, i = 2, j 从i -> 0遍历:
j=2, j >0,
因为array[j] = com.java.datastructure.array[j-1],所以什么都不做.
j--, j=1,
因为array[j] > com.java.datastructure.array[j-1],所以什么都不做。
do j--, j=0, 所以退出内层for循环.

{0, 28, 28, 27, 1, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9


do i++, i = 3, j从 i -> 0遍历:
j=i=3, j>0,
因为array[j] < com.java.datastructure.array[j-1],即后一个元素小于前一个元素，所以交换28和27:

{0, 28, 27, 28, 1, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9
 
j--,j=2,
因为array[j] < com.java.datastructure.array[j-1],即后一个元素小于前一个元素，所以交换28和27:

{0, 27, 28, 28, 1, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9

j--,j=1,
因为array[j]>com.java.datastructure.array[j-1],所以do nothing.
do j--,j=0,所以退出内层for循环.

do i++, i = 4, j从i -> 0遍历.
j=i=4, j>0,
因为array[j] < com.java.datastructure.array[j-1],所以交换1和28:

{0, 27, 28, 1, 28, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9
do j--,j=3,
com.java.datastructure.array[j]<com.java.datastructure.array[j-1],交换1和28:

{0, 27, 1, 28, 28, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9

do j--,j=2,
com.java.datastructure.array[j]<com.java.datastructure.array[j-1],交换1和27:
{0, 1, 27, 28, 28, 0, 24, 6, 4, 4}
 0	 1   2  3   4  5   6  7  8  9

do j--,j=1,
com.java.datastructure.array[j]>com.java.datastructure.array[j-1],所以do nothing.
j--,j=0,所以退出内层for循环.

依次类推
-------------------------------------------------------------
