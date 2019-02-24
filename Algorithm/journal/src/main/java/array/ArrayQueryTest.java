package array;

public class ArrayQueryTest {

    public static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
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
            return this.id == user.id;
        }
    }

    public static void main(String[] args) {
        Array<User> userArray=new Array<User>(5);

        userArray.insert(new User(1,"xzj"));
        userArray.insert(new User(2,"xzj2"));
        userArray.insert(new User(3,"xzj3"));

        //根据user的id进行查找
        int queryKeyWord=2;

        User result=null;//查询结果
        for (int i = 0; i < userArray.size(); i++) {
            User user=userArray.get(i);
            if(queryKeyWord==user.id){
                System.out.println(user);
                break;
            }
        }

        boolean removeResult = userArray.remove(new User(1,"xzj"));
        System.out.println(removeResult);
        System.out.println(userArray.size());
    }
}
