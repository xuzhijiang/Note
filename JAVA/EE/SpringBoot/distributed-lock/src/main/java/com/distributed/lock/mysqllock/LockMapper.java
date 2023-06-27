package com.distributed.lock.mysqllock;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LockMapper {

    // 如果没有用到索引,for update会失效
    //
    // 当表里面只有一条数据的时候,此时就不会用到索引,因为mysql会给你优化, 所以这种情况下,索引就没有用到,所以for update失效了.
    //
    // 当for update失效的时候,并没有把这个数据给锁住,所以锁就失效了
    // 所以测试的时候,要多插入几条数据,然后for update才可以生效
    @Select("select count(1) from distributed_lock where lock_name = #{lockName} for update")
    public int selectForUpdate(@Param("lockName") String lockName);

    @Select("select count(*) from distributed_lock")
    public int select();
}
