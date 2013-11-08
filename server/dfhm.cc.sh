#!/bin/sh

fuser -k -u -n file ../log/dfhm.cc.log

sleep 2

>../log/dfhm.cc.log

/usr/bin/java -Dfile.encoding=UTF-8 -cp log4j-1.2.11.jar:mysql-5.1.6.jar:zlib.zip:zmyth.zip:youkia.zip:. zmyth.xlib.Start cc.waw.start.cfg >../log/dfhm.cc.log 2>>../log/dfhm.cc.log &

tail ../log/dfhm.cc.log -f
