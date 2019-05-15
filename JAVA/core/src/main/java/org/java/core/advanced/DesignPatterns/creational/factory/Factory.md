## 工厂模式

工厂模式的意义在于对象的创建、管理可以使用工厂去管理，而不是创建者自身。最典型的工厂模式使用者就是Spring，Spring内部的容器就是一个工厂，所有的bean都由这个容器管理，包括它们的创建、销毁、注入都被这个容器管理。

工厂模式分简单工厂和抽象工厂。它们的区别在于抽象工厂抽象程度更高，把工厂也抽象成了一个接口，这样可以再每添加一个新的对象的时候而不需要修改工厂的代码。



```java
// 比如有个Repository接口，用于存储数据，有DatabaseRepository，CacheRepository，
// FileRepository分别在数据库，缓存，文件中存储数据，定义如下：
public interface Repository {
    void save(Object obj);
}

class DatabaseRepository implements Repository {
    @Override
    public void save(Object obj) {
        System.out.println("save in database");
    }
}
class CacheRepository implements Repository {
    @Override
    public void save(Object obj) {
        System.out.println("save in cache");
    }
}

class FileRepository implements Repository {
    @Override
    public void save(Object obj) {
        System.out.println("save in file");
    }
}

// 简单工厂的使用
// 简单工厂的弊端在于每添加一个新的Repository，都必须修改RepositoryFactory中的代码
public class RepositoryFactory {

    public Repository create(String type) {
        Repository repository = null;
        switch (type) {
            case "db":
                repository = new DatabaseRepository();
                break;
            case "cache":
                repository = new CacheRepository();
                break;
            case "file":
                repository = new FileRepository();
                break;
        }
        return repository;
    }

    public static void main(String[] args) {
        RepositoryFactory factory = new RepositoryFactory();
        factory.create("db").save(new Object());
        factory.create("cache").save(new Object());
        factory.create("file").save(new Object());
    }
}

// 抽象工厂的使用
public interface RepositoryFactoryProvider {
    Repository create();
}

class DatabaseRepositoryFactory implements RepositoryFactoryProvider {
    @Override
    public Repository create() {
        return new DatabaseRepository();
    }
}

class CacheRepositoryFactory implements RepositoryFactoryProvider {
    @Override
    public Repository create() {
        return new CacheRepository();
    }
}

class FileRepositoryFactory implements RepositoryFactoryProvider {
    @Override
    public Repository create() {
        return new FileRepository();
    }
}

// 抽象工厂把工厂也进行了抽象化，所以添加一个新的Repository的话，
// 只需要新增一个RepositoryFactory即可，原有代码不需要修改。
// 抽象工厂的测试：

RepositoryFactoryProvider dbProvider = new DatabaseRepositoryFactory();
dbProvider.create().save(new Object());
RepositoryFactoryProvider cacheProvider = new CacheRepositoryFactory();
cacheProvider.create().save(new Object());
RepositoryFactoryProvider fileProvider = new FileRepositoryFactory();
fileProvider.create().save(new Object());
```

### JDK中工厂模式的实例

包装类中的valueOf()方法，如Integer等,valueOf() method in wrapper classes like Boolean, Integer etc.