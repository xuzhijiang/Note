选择排序

选择排序算法通过从未排序部分重复查找最小元素（考虑升序）
并将其放在开头来对数组进行排序。 该算法在给定数组中维护两个子数组。

1）已经排序的子阵列。
2）未分类的剩余子阵列。

在选择排序的每次迭代中，挑选来自未排序子阵列的最小元素（考虑升序）并将其移动到排序的子阵列。

以下示例解释了上述步骤:

--------------------------------------------------------

{64, 25, 12, 22, 11}
 0    1   2   3   4

Find the minimum element in arr[0...4]
and place it at beginning

{11, 25, 12, 22, 64}
 0    1   2   3   4

Find the minimum element in arr[1...4]
and place it at beginning of arr[1...4]

{11, 12, 25, 22, 64}
 0    1   2   3   4

Find the minimum element in arr[2...4]
and place it at beginning of arr[2...4]

{11, 12, 22, 25, 64}
 0    1   2   3   4

Find the minimum element in arr[3...4]
and place it at beginning of arr[3...4]

{11, 12, 22, 25, 64}
 0    1   2   3   4