# LinkedBlockingQueue(多线程安全)

     ArrayBlockingQueue中,如果队列满了,此时再往队列中添加数据,线程会被阻塞，需要其他线程消费数据才能唤醒.
     
     而往LinkedBlockingQueue中添加数据的时候,如果被阻塞,不仅在消费数据的时候会唤醒被阻塞的插入数据的线程，
     同时在其他插入数据的线程发现容量还没满的时候，也会唤醒被阻塞的插入数据的线程,这是因为它内部有2个锁，
     可以并行执行放入数据和消费数据
     
     这也是LinkedBlockingQueue吞吐量更高的原因.