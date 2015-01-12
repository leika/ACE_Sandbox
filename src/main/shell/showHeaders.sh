#!/bin/sh

tcpdump -i lo0 -nS -s 1024 -A dst port 4080 and tcp and greater 200