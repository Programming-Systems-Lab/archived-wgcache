echo Compiling sources for Workgroup Cache
cd\pslcvs
javac -classpath .;c:\pslcvs;c:\pslcvs\jars\siena-1.1.2.jar;c:\pslcvs\jars\hsql.jar;c:\pslcvs\psl\jars\xerces.jar;c:\pslcvs\psl\kx\KXNotification.java;c:\pslcvs\psl\wgcache\*.java;c:\pslcvs\psl\xues\*.java
rmic psl.wgcache.WorkgroupManagerImpl psl.wgcache.RMI_PCMImpl
