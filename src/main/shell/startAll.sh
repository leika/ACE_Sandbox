#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
mkdir -p $DIR/logs



appName="ACESandbox"



if [ "$1" = "help" ]
then
	$DIR/SandboxClient/bin/SandboxMain help
	exit
fi

$DIR/startWeb.sh

export JAVA_OPTS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=BackServer
-Dappdynamics.agent.nodeName=tcp1"


$DIR/TCPBackend/bin/TCPBackend start -port 8989 >> $DIR/logs/tcpBackend.log &

backend_pid=$!

export JAVA_OPTS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=BackServer_Middle
-Dappdynamics.agent.nodeName=tcp2"

$DIR/TCPBackend/bin/TCPBackend start -port 8999 >> $DIR/logs/tcpBackend2.log &

backend2_pid=$!

export JAVA_OPTS="-javaagent:$DIR/agent/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=Frontend
-Dappdynamics.agent.nodeName=Client1"
$DIR/SandboxClient/bin/SandboxMain $1

kill $backend_pid
kill $backend2_pid

$DIR/stopWeb.sh
