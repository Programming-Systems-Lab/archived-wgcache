echo Compiling sources for Workgroup Cache

javac -d %PSLROOT% -classpath .:%PSLROOT%:%PSLROOT%\psl\jars\siena-1.1.2.jar:%PSLROOT%\psl\jars\hsql.jar:%PSLROOT%\psl\jars\xerces.jar %PSLROOT%\psl\kx\KXNotification.java %PSLROOT%\psl\wgcache\*.java %PSLROOT%\psl\xues\*.java
rmic -d %PSLROOT% -classpath %PSLROOT% psl.wgcache.WorkgroupManagerImpl psl.wgcache.RMI_PCMImpl
