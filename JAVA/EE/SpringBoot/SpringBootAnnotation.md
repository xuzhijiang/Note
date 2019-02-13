介绍Spring MVC中最为常用的几个核心注解：@Controller,@RestController,RequestMapping以及一些参数绑定的注解：@PathVariable,@ModelAttribute,@RequestParam:

* @Controller：修饰class，用来创建处理http请求的对象
* @RestController：Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody来配合，如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式。
* @RequestMapping：配置url映射
* @PathVariable:url中的参数可通过@PathVariable绑定到函数的参数中