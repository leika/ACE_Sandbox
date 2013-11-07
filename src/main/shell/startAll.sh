#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
mkdir -p $DIR/logs

appName="ACESandbox"

$DIR/startWeb.sh

export JAVA_OPTS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=BackServer
-Dappdynamics.agent.nodeName=tcp1"


$DIR/TCPBackend/bin/TCPBackend  >> $DIR/logs/tcpBackend.log &

backend_pid=$!

export JAVA_OPTS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=Frontend
-Dappdynamics.agent.nodeName=Client1"
$DIR/SandboxClient/bin/SandboxMain

kill $backend_pid

$DIR/stopWeb.sh
