#!/bin/bash

version=${1}

case "$version" in 
    "8")
        export JAVA_HOME=/home/xzj/opt/jdk1.8.0_211
        export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
        export PATH=$JAVA_HOME/bin:$PATH
        java -version
        ;;
    "6")
        export JAVA_HOME=/usr/java/jdk1.6.0_45
        export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
        export PATH=$JAVA_HOME/bin:$PATH
        java -version
        ;;
    *)
        echo "what do you wanna ?!"
        ;;
esac