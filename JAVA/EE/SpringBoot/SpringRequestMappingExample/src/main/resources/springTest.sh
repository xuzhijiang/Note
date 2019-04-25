#!/bin/bash

# Spring RestMapping测试程序

# 我们可以使用Spring RestTemplate来测试上面的不同方法，

# 但今天我将使用cURL命令(catch url)来测试这些方法，因为这些方法很简单并且没有太多数据流动。

# 我创建了一个简单的shell脚本springTest.sh来调用所有上述方法并打印它们的输出。 它看起来像下面。

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method0";
curl http://localhost:9090/SpringRequestMappingExample/home/method0;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home";
curl http://localhost:9090/SpringRequestMappingExample/home;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/xyz";
curl http://localhost:9090/SpringRequestMappingExample/home/xyz;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method1";
curl http://localhost:9090/SpringRequestMappingExample/home/method1;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method1/second";
curl http://localhost:9090/SpringRequestMappingExample/home/method1/second;
printf "\n\n*****\n\n";

echo "curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method2";
curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method2;
printf "\n\n*****\n\n";

echo "curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method3";
curl -X POST http://localhost:9090/SpringRequestMappingExample/home/method3;
printf "\n\n*****\n\n";

echo "curl -X GET http://localhost:9090/SpringRequestMappingExample/home/method3";
curl -X GET http://localhost:9090/SpringRequestMappingExample/home/method3;
printf "\n\n*****\n\n";

echo "curl -H "name:pankaj" http://localhost:9090/SpringRequestMappingExample/home/method4";
curl -H "name:pankaj" http://localhost:9090/SpringRequestMappingExample/home/method4;
printf "\n\n*****\n\n";

echo "curl -H "name:pankaj" -H "id:1" http://localhost:9090/SpringRequestMappingExample/home/method5";
curl -H "name:pankaj" -H "id:1" http://localhost:9090/SpringRequestMappingExample/home/method5;
printf "\n\n*****\n\n";

echo "curl -H "Content-Type:text/html" http://localhost:9090/SpringRequestMappingExample/home/method6";
curl -H "Content-Type:text/html" http://localhost:9090/SpringRequestMappingExample/home/method6;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method6";
curl http://localhost:9090/SpringRequestMappingExample/home/method6;
printf "\n\n*****\n\n";

echo "curl -H "Content-Type:text/html" -H "Accept:application/json" -i http://localhost:9090/SpringRequestMappingExample/home/method6";
curl -H "Content-Type:text/html" -H "Accept:application/json" -i http://localhost:9090/SpringRequestMappingExample/home/method6;
printf "\n\n*****\n\n";

echo "curl -H "Content-Type:text/html" -H "Accept:application/xml" -i http://localhost:9090/SpringRequestMappingExample/home/method6";
curl -H "Content-Type:text/html" -H "Accept:application/xml" -i http://localhost:9090/SpringRequestMappingExample/home/method6;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method7/1";
curl http://localhost:9090/SpringRequestMappingExample/home/method7/1;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method8/10/Lisa";
curl http://localhost:9090/SpringRequestMappingExample/home/method8/10/Lisa;
printf "\n\n*****\n\n";

echo "curl http://localhost:9090/SpringRequestMappingExample/home/method9?id=20";
curl http://localhost:9090/SpringRequestMappingExample/home/method9?id=20;
printf "\n\n*****DONE*****\n\n";

