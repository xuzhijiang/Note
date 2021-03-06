### 插入排序基本步骤

    以降序排列为例，一个n个元素的数组，外层循环需要遍历n-1次(n-1轮)，每一次遍历(每轮)都确定一个位置的最终元素是哪一个：
    
    第一轮:将下标为0的元素和下标在[1,n-1]之间的数字逐一进行比较，每次遇到下标比0位置
    大的元素，就进行交换，当第一轮排序完成后，下标为0的元素就是整个数组中最大的元素.
    
    第二轮:将下标为1的元素和下标在[2,n-1]之间的元素逐一比较，每次遇到下标比1位置大的元素
    ，就进行交换，第二轮排序完成后，下标为1的元素就是整个数组中第二大的元素.
    
    第N-1轮:将下标为n-2的元素和下标在[n-1,n-1]之间的元素逐一比较，每次遇到下标比n-2大的元素
    就进行交换，把比较大的元素放到n-2的位置，这样排序完成后，n-2位置的元素就是剩余元素中最大
    的元素，至此，排序完成.
    
    外层: i -> [0, n-2]
    内层: j -> [1, n-1]

### 时间复杂度

>对于排序算法的时间复杂度分析，要从2个角度考虑，一个是比较的次数，一个是交换的次数.
对于n个元素的数组，需要进行n-1轮的排序，每i轮排序，需要进行n-i次的比较.

    比较次数的时间复杂度为(比较是必须的):
    
    第一轮: 比较n-1次
    第二轮: 比较n-2次
    ...
    第n-1轮: 比较1次
    
    C(max) = n(n-1)/2 = O(n2)           注:C表示compare
    
    交换次数的时间复杂度(交换不是必须的):
    
    在比较过程中，交换不是必须的，只有在顺序不对的情况下，才会交换。
    如果数组是由小到大排序的，但是我们希望由大到小排序，这个时候每次比较都需要进行交换，
    这个时候达到交换的最大次数，且每次比较都必须移动记录3次来交换记录位置:
    
    M(max) = 3n(n-1)/2 = O(n2)  注:max表示最大耗时
    
    综述:插入排序总的时间复杂度为O(n2).
