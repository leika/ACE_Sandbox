#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"



export JAVA_OPTIONS=""
export JAVA_OPTS=""
$DIR/web/node4080/bin/jetty.sh stop

