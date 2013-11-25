#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

appName="ACESandbox"

export JETTY_ARGS="jetty.port=4080"
export JAVA_OPTIONS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.controller.hostName=$controllerHost
-Dappdynamics.controller.port=$controllerPort
-Dappdynamics.agent.tierName=WebBackend
-Dappdynamics.agent.nodeName=Node4080"

$DIR/web/node4080/bin/jetty.sh start


