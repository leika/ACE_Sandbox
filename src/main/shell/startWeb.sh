#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

appName="ACESandbox"

export JETTY_ARGS="jetty.port=4080"
export JAVA_OPTIONS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.controller.hostName=$controllerHost
-Dappdynamics.controller.port=$controllerPort
-Dappdynamics.agent.tierName=WebBackend
-Dappdynamics.agent.nodeName=Node4080
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9991
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false"

$DIR/web/node4080/bin/jetty.sh start


