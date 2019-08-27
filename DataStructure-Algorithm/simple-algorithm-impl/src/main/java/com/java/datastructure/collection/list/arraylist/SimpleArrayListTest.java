package com.java.datastructure.collection.list.arraylist;

import org.junit.Test;

public class SimpleArrayListTest {

    /**
     * 测试自定义的Array
     */
    @Test
    public void arrayTest() {
        Array<Integer> array = new Array<Integer>(5);
        array.insert(1);
        array.insert(5);
        array.insert(3);
        array.display("初始 1、5、3 ： ");
        array.insert(4);
        array.insert(6);
        array.display("添加 4,6       ： ");
        array.remove(3);
        array.display("删除 3       ： ");
        System.out.println();
        System.out.println("查找 4："+array.find(4) );
        System.out.println("查找 3："+array.find(3) );

        System.out.println("#################################");

        Array<User> userArray = new Array<>(5);

        userArray.insert(new User(1,"xzj"));
        userArray.insert(new User(2,"xzj2"));
        userArray.insert(new User(3,"xzj3"));

        // 根据user的id进行查找
        int id = 2;
        User result=null;//查询结果
        for (int i = 0; i < userArray.size(); i++) {
            User user=userArray.get(i);
            if(id==user.getId()){
                System.out.println("找到id为" + id + "的用户了: " + user);
                break;
            }
        }

        boolean removeResult = userArray.remove(new User(1,"xzj"));
        System.out.println(removeResult);

        userArray.display("所有的user: ");
    }

    @Test
    public void testInsert() {
        // add 测试
        // 注意我们使用的无参构造方法，因此默认容量大小是16 而我们插入的是20个元素，因此会自动扩容
        SimpleArrayList<Integer> list = new SimpleArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        System.out.println("size:" + list.size() + ",capacity:" + list.capacity());

        // get测试
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+" ");
        }

        // delete测试
        //删除index为10的元素
        list.remove(10);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("index:"+i+";value:"+list.get(i));
        }
        System.out.println("size:" + list.size() + ",capacity:" + list.capacity());
    }

    // 定义内部测试类
    private static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            User user = (User)obj;
            return this.id == user.id && this.name.equals(user.getName());
        }
    }

}
