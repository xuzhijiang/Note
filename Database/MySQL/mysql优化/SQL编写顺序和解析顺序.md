# SQL编写顺序和解析顺序

```sql
-- 编写顺序: select distinct ..from ..join ..on ..where ..group by ..having ...order by ..limit...
SELECT DISTINCT
    < select_list >
FROM
    < left_table > < join_type >
JOIN < right_table > ON < join_condition >
WHERE
    < where_condition >
GROUP BY
    < group_by_list >
HAVING
    < having_condition >
ORDER BY
    < order_by_condition >
LIMIT < limit_number >
```

```sql
-- 解析顺序: from ..on..  join... where... group by ...having...  select distinct .. order by limit...
FROM <left_table>
ON <join_condition>
<join_type> JOIN <right_table>
WHERE <where_condition>
GROUP BY <group_by_list>
HAVING <having_condition>
SELECT 
DISTINCT <select_list>
ORDER BY <order_by_condition>
LIMIT <limit_number>
```

- [每个关键字的说明文章](https://www.cnblogs.com/annsshadow/p/5037667.html)
