@echo off
echo Compiling sources for Workgroup Cache
cd\pslcvs

echo Compiling Workgroup Cache and Xues
javac -classpath .;c:\pslcvs;c:\pslcvs\jars\siena-1.1.2.jar;c:\pslcvs\jars\hsql.jar;c:\pslcvs\jars\xerces.jar c:\pslcvs\psl\kx\KXNotification.java c:\pslcvs\psl\wgcache\*.java c:\pslcvs\psl\xues\*.java

echo Creating RMI stubs
rmic psl.wgcache.WorkgroupManagerImpl psl.wgcache.RMI_PCMImpl

echo Create wgc.jar
jar cf jars\wgc.jar psl\wgcache\*.class psl\xues\*.class psl\kx\*.class