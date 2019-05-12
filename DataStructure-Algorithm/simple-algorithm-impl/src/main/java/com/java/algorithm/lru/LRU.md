## LRU

LRU是`Least Recently Used`缩写，即`最近最少使用`.通常用于实现缓存的淘汰策略,由于缓存使用的内存非常宝贵，
所以需要跟某种规则来剔除数据保证内存不被撑满.

### 如常用的 Redis 就有以下几种策略：

<table>
<thead>
    <tr>
       <th>规则</th>
       <th>说明</th>
    </tr>
</thead>
<tbody>
    <tr>
       <td>volatile-lru</td>
       <td>从已设置过期时间的数据集中挑选最近最少使用的数据淘汰</td>
    </tr>
    <tr>
       <td>volatile-ttl</td>
       <td>从已设置过期时间的数据集中挑选将要过期的数据淘汰</td>
    </tr>
    <tr>
       <td>volatile-random</td>
       <td>从已设置过期时间的数据集中任意选择数据淘汰</td>
    </tr>
    <tr>
       <td>allkeys-lru</td>
       <td>从所有数据集中挑选最近最少使用的数据淘汰</td>
    </tr>
    <tr>
       <td>allkeys-random</td>
       <td>从所有数据集中任意选择数据进行淘汰</td>
    </tr>
    <tr>
       <td>noeviction</td>
       <td>不删除键，只返回错误</td>
    </tr>
</tbody>
</table>


