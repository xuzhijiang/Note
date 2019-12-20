#!/bin/sh
# Tomcat init script for Linux.
 
JAVA_HOME=/usr/bin/java
CATALINA_HOME=/opt/tomcat
export JAVA_HOME CATALINA_HOME
 
start() {
       $CATALINA_HOME/bin/catalina.sh configtest &> /dev/null
       if [ $? -ne 0 ];then
              echo "Error in configuration file,check with tomcat configuration file."
              exit 5
       fi
 
       if pidof java &> /dev/null;then
              echo "Tomcat is running...."
              exit 4
       else
              exec $CATALINA_HOME/bin/catalina.sh start
       fi
 
}

stop() {
       pidof java &> /dev/null
       if [ $? -ne 0 ];then
              echo "Tomcat is stoped..."
       else
              $CATALINA_HOME/bin/catalina.sh stop
       fi
}
 
configtest() {
       pidof java &> /dev/null
       if [ $? -eq 0 ];then
              echo "Tomcat is running,please stop the test."
              exit 3
       else
              exec $CATALINA_HOME/bin/catalina.sh configtest
       fi
}

version() {
       exec $CATALINA_HOME/bin/catalina.sh version
}
 
case $1 in
       start)
              # 执行start函数
              start ;;
       stop)
              stop ;;
       restart)
              stop
              sleep 1
              start ;;
       configtest)
              configtest ;;
       version)
              version ;;
       *)
              echo "Usage: `basename $0` {start|stop|restart|configtest|version}"
              exit 1 ;;
esac
