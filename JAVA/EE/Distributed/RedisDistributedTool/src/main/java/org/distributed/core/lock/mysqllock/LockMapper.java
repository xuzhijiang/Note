package org.distributed.core.lock.mysqllock;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LockMapper {

    @Select("select count(1) from distributed_lock where lock_name = #{lockName}")
    public int get(@Param("lockName") String lockName);

    @Insert("insert into distributed_lock(lock_name) values (#{lockName})")
    public void insert(@Param("lockName") String lockName);

    @Delete("delete from distributed_lock where lock_name = #{lockName}")
    public void delete(@Param("lockName") String lockName);

    // 如果没有用到索引,for update会失效,当表里面只有一条数据的时候,就不会用到索引,因为mysql会给你优化
    // 这种情况下,索引就没有用到,所以for update失效了.
    // 所以for update失效的时候,并没有把这个数据给锁住,所以锁就失效了
    // 所以说,MysqlLock和MySqlForUpdateLock这两种实现方式都不太好.
    // 当然你也可以多插入几条数据,然后for update就生效了.
    @Select("select count(1) from distributed_lock where lock_name = #{lockName} for update")
    public int getForUpdate(@Param("lockName") String lockName);
}
