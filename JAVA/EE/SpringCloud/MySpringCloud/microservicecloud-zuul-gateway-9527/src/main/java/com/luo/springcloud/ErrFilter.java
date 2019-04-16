package com.luo.springcloud;

import com.netflix.zuul.ZuulFilter;

/**
 * 本类目前没有使用.
 *
 * 服务过滤,在配置完服务网关之后, 我们还需要配置服务filter，来限制客户端只访问到指定访问的资源，提高系统的安全性。
 *
 * 在定义zuul网关服务过滤只需要创建一个继承ZuulFilter抽象类并重写四个方法即可：
 *
 * 1. filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
 *      a. pre：可以在请求被路由之前调用；
 *      b. routing：在路由请求的时候被调用；
 *      c. post：在routing和error过滤器之后被调用；
 *      d. error：在请求发生错误的时候被调用
 *
 * 2. filterOrder：通过int值来定义过滤器的执行顺序
 * 3. shouldFilter：返回一个boolean类型来判断该过滤器是否要执行，所以通过此函数可实现过滤器的开关。在上例中，我们直接返回true，所以该过滤器总是生效。
 * 4. run：过滤器的具体逻辑。需要注意，这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，
 * 不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，
 * 当然我们也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回body内容进行编辑等。
 */
public class ErrFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() {
        return null;
    }
}
