Service是一种应用程序的组件，可以在后台执行长时间运行的操作
(can perform long-running operations in the backgound.）
但不提供用户界面。另一个应用程序组件可以启动服务，即使用户切换到另一个应用程序，
它也将继续在后台运行。此外，组件可以绑定到服务以与其交互，甚至可以执行进程间通信
(IPC-inter-process communication）。
例如，服务可以从后台处理网络事务，播放音乐，执行文件I / O或与内容提供者交互,以及任何和
UI没有关联的处理的时候会被使用.

Started A service is "started" when an application component 
(such as an activity) starts it by calling startService().
一旦启动，服务可以无限期(indefinitely)地在后台运行,，即使启动它的组件被销毁。通常，
启动的服务执行单个操作，并且不会将结果返回给调用者。例如，
它可能通过网络下载或上载文件。操作完成后，服务应自行停止。

当组件通过调用bindService(）绑定到Service，Service被“绑定”。
绑定服务提供客户端 - 服务器接口，允许组件与Service交互，发送请求，获取结果，甚至跨进程通信(IPC）进程。
只要绑定了另一个应用程序组件，绑定服务就会运行。多个组件可以绑定到服务，但是当所有组件解除绑定时，服务将被销毁。

警告：A service runs in the main thread of its hosting process(它托管进程的主线程中) 
- 该服务不会创建自己的线程，也不会在单独的进程中运行(除非您另行指定）。

 这意味着，如果您的服务要进行任何CPU密集型工作或阻止操作(any CPU intensive work or blocking operations)
 (例如MP3播放或网络），您应该在服务中创建一个新线程来完成这项工作。

在Service中，By using a separate thread, you will reduce the risk of 
Application Not Responding (ANR) errors.
 
 main thread can remain dedicated to user interaction with your activities.
 (主线程可以保持专注于用户和你的Activities的交互)
 
bind service的不同之处在于当绑定的组件销毁后，对应的service也就被kill了.

Service确实是运行在主线程里的，也就是说如果你在Service里编写了非常耗时的代码，程序必定会出现ANR的。

你可能会惊呼，这不是坑爹么！？那我要Service又有何用呢？其实大家不要把后台和子线程联系在一起就行了，
这是两个完全不同的概念。Android的后台就是指，它的运行是完全不依赖UI的。即使Activity被销毁，
或者程序被关闭，只要进程还在，Service就可以继续运行。比如说一些应用程序，始终需要与服务器之间始终
保持着心跳连接，就可以使用Service来实现。你可能又会问，前面不是刚刚验证过Service是运行在主线程里
的么？在这里一直执行着心跳连接，难道就不会阻塞主线程的运行吗？
当然会，但是我们可以在Service中再创建一个子线程，然后在这里去处理耗时逻辑就没问题了。

额，既然在Service里也要创建一个子线程，那为什么不直接在Activity里创建呢？这是因为Activity很难对Thread进行控制，
当Activity被销毁之后，就没有任何其它的办法可以再重新获取到之前创建的子线程的实例。
而且在一个Activity中创建的子线程，另一个Activity无法对其进行操作。但是Service就不同了，
所有的Activity都可以与Service进行关联，然后可以很方便地操作其中的方法，即使Activity被销毁了，
之后只要重新与Service建立关联，就又能够获取到原有的Service中Binder的实例。因此，
使用Service来处理后台任务，Activity就可以放心地finish，完全不需要担心无法对后台
任务进行控制的情况。

Service 就像一个Activity，但没有界面。 

如果是Local Service，那么Service 运行在主进程的 main 线程上的。
如：onCreate，onStart 这些函数在被系统调用的时候都是在主进程的 main 线程上运行的。
如果是Remote Service，那么对应的 Service 则是运行在独立进程的 main 线程上。
因此请不要把 Service 理解成线程，它跟线程半毛钱的关系都没有！

任何 Activity 都可以控制同一 Service，而系统也只会创建一个对应 Service 的实例,但是
任何的Activity不能控制同一Thread，所以这就是Thread和Service的区别.

Service在有 Context 的地方调用 Context.startService、Context.stopService、
Context.bindService，Context.unbindService，来控制它，
你也可以在 Service 里注册 BroadcastReceiver，
在其他地方通过发送 broadcast 来控制它，当然这些都是 Thread 做不到的。

startService:

     如果一个Service被startService方法多次启动，那么onCreate方法只会调用一次，
     onStart将会被调用多次(对应调用startService的次数），并且系统只会创建Service的一个实例
    (因此你应该知道只需要一次stopService调用）。该Service将会一直在后台运行，
    而不管对应程序的Activity是否在运行，直到被调用stopService，或自身的stopSelf方法。
    当然如果系统资源不足，android系统也可能结束服务。
    Note: 使用 startService 启动服务之后，一定要使用 stopService停止服务，不管你是否使用bindService； 

bindService:

     如果一个Service被某个Activity调用 Context.bindService方法绑定启动，
     不管调用 bindService调用几次，onCreate方法都只会调用一次，同时onStart方法始终不会被调用。
     当连接建立之后，Service将会一直运行，除非调用Context.unbindService断开连接  or 之前调用
    bindService的Context不存在了(如Activity被finish的时候），
    系统将会自动停止Service，对应onDestroy将被调用。

    同时使用 startService 与 bindService 要注意到，Service 的终止，需要unbindService与stopService同时调用，
    才能终止 Service，不管 startService 与 bindService 的调用顺序，如果先调用 unbindService 
    此时服务不会自动终止，再调用 stopService 之后服务才会停止，如果先调用 stopService 此时服务也不会终止，
    而再调用 unbindService 或者 之前调用 bindService 的 Context 不存在了(如Activity 被 finish
     的时候）之后服务才会自动停止；

 当一个Service被终止(1、调用stopService；
 2、调用stopSelf；3、不再有绑定的连接(没有被启动））时，onDestroy方法将会被调用，
 在这里你应当做一些清除工作，如停止在Service中创建并运行的线程
 
 在 sdk 2.0 及其以后的版本中，对应的 onStart 已经被否决变为了 onStartCommand
 
 无法再子线程中处理UI相关更新操作，要使用handler在main thread做更新ui的操作.

 在BroadcastReceiver中不可以bindService()，只能startService()