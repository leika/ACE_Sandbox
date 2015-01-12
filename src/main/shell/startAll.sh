#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
mkdir -p $DIR/logs

# Change this if needed
#


export AGENTDIR=$DIR/agent/


appName="ACESandbox"



if [ "$1" = "help" ]
then
	$DIR/SandboxClient/bin/SandboxMain help
	exit
fi

$DIR/startWeb.sh

export JAVA_OPTS="-javaagent:$AGENTDIR/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.agent.tierName=BackServer
-Dappdynamics.controller.hostName=${controllerHost-localhost}
-Dappdynamics.controller.port=${controllerPort-8080}
-Dappdynamics.agent.nodeName=tcp1
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9203
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false"


$DIR/TCPBackend/bin/TCPBackend start -port 8989 >> $DIR/logs/tcpBackend.log &

backend_pid=$!

export JAVA_OPTS="-javaagent:$AGENTDIR/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.controller.hostName=${controllerHost-localhost}
-Dappdynamics.controller.port=${controllerPort-8080}
-Dappdynamics.agent.tierName=BackServer_Middle
-Dappdynamics.agent.nodeName=tcp2
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9202
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false"

$DIR/TCPBackend/bin/TCPBackend start -port 8999 >> $DIR/logs/tcpBackend2.log &

backend2_pid=$!

export JAVA_OPTS="-javaagent:$AGENTDIR/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.controller.hostName=${controllerHost-localhost}
-Dappdynamics.controller.port=${controllerPort-8080}
-Dappdynamics.agent.tierName=BackServer_RMI
-Dappdynamics.agent.nodeName=tcp2
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9208
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false"

$DIR/RMIBackend/bin/RMIBackend start -port 9099 >> $DIR/logs/tcpBackend_RMI.log &

backend3_pid=$!

export JAVA_OPTS="-javaagent:$AGENTDIR/javaagent.jar
-Dappdynamics.agent.applicationName=$appName
-Dappdynamics.controller.hostName=${controllerHost-localhost}
-Dappdynamics.controller.port=${controllerPort-8080}
-Dappdynamics.agent.tierName=Frontend
-Dappdynamics.agent.nodeName=Client1
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9201
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false"
$DIR/SandboxClient/bin/SandboxMain $1

kill $backend_pid
kill $backend2_pid
kill $backend3_pid

$DIR/stopWeb.sh
