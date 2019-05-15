## 复合设计模式

`组合模式`经常跟`策略模式`配合使用，用来组合所有的策略，并遍历这些策略找出满足条件的策略。

### 实例

>之前写过一篇SpringMVC关于json、xml自动转换的原理研究文章，里面springmvc把返回的返回值映射给用户的response做了一层抽象，封装到了HandlerMethodReturnValueHandler策略接口中。在HandlerMethodReturnValueHandlerComposite类中，使用存在的HandlerMethodReturnValueHandler对返回值进行处理，在HandlerMethodReturnValueHandlerComposite内部的代码如下：

```java
public class Demo {
    // 策略集合
    private final List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<HandlerMethodReturnValueHandler>();
    
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
        ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        
        // 调用selectHandler方法(过滤找到满足条件的策略)
        HandlerMethodReturnValueHandler handler = selectHandler(returnValue, returnType);
        if (handler == null) {
          throw new IllegalArgumentException("Unknown return value type: " + returnType.getParameterType().getName());
        }
        // 使用满足条件的策略处理
        handler.handleReturnValue(returnValue, returnType, mavContainer, webRequest); // 使用找到的handler进行处理
        
    }
    
    // 过滤满足条件的策略
    private HandlerMethodReturnValueHandler selectHandler(Object value, MethodParameter returnType) {
        boolean isAsyncValue = isAsyncReturnValue(value, returnType);
        // 遍历存在的HandlerMethodReturnValueHandler
        for (HandlerMethodReturnValueHandler handler : this.returnValueHandlers) {
          if (isAsyncValue && !(handler instanceof AsyncHandlerMethodReturnValueHandler)) {
                continue;
          }
          
          if (handler.supportsReturnType(returnType)) { // 找到匹配的handler(找到满足条件的策略)
                return handler;
          }
        }
        return null;
    }
}
```