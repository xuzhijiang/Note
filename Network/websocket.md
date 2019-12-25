# http和socket的区别

    http请求必须先由client发起,server不能主动向client推送msg.(server是不能主动发起http请求的)

    socket: socket通常指的是tcp的socket,tcp的socket,一般上来就是client和server建立一个通道,这个通道就是长连接.
    在这个长连接通道上,client和server都可以主动发消息.这个因为是一个长连接导致的结果.
    
    socket:server能主动推送消息到client,client也能主动发送msg

>但是我们大多数时候使用的是http来发送请求,但是有时候又需要server主动发送消息,所以html5就退出了websocket.

    没有websocket之前,前端主要是通过client发http请求到server,不断的轮询,每隔一段时间,看看server有没有消息
    
    有了websocket之后,server可以主动通知client.,这时候就不用轮询了.

    大量的应用在网页聊天室,状态变更比较频繁的股票信息等等,
