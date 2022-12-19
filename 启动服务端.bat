@echo off
@title WAMS
Color 0A
set path=jre\bin;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%
set CLASSPATH=.;dist\*;dist\lib\*;

java -Xmx14G -Xms14G -XX:NewSize=13G -XX:MaxNewSize=13G -XX:SurvivorRatio=10 -XX:MetaspaceSize=1G -XX:MaxMetaspaceSize=1G -XX:-UseConcMarkSweepGC -Dnet.sf.odinms.wzpath=wz gui.LZJMS

pause &