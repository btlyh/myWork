#!/bin/sh

fuser -k -u -n file ../log/dc.waw.log

sleep 2

>../log/dc.waw.log

/home/fun/jdk1.5/jre/bin/java -Xms500M -Xmx1000M -Dfile.encoding=UTF-8 -cp log4j-1.2.11.jar:mysql-5.1.6.jar:zlib.zip:zmyth.zip:youkia.zip:. zmyth.xlib.Start dc.waw.start.cfg >../log/dc.waw.log 2>>../log/dc.waw.log &


tail ../log/dc.waw.log -f
