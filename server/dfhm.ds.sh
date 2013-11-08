#!/bin/sh

fuser -k -u -n file ../log/dfhm.log

sleep 2

>../log/dfhm.log

/home/fun/jdk1.5/jre/bin/java -Xms500M -Xmx1001M -Dfile.encoding=UTF-8 -cp log4j-1.2.11.jar:mysql-5.1.6.jar:zlib.zip:zmyth.zip:youkia.zip:. zmyth.xlib.Start dfhm.start.cfg >../log/dfhm.log 2>>../log/dfhm.log &


tail ../log/dfhm.log -f
