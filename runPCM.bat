echo Executing Workgroup Cache - the Personal Cache Module

java -DUser=gskc -DManager=rmi://canal.psl.cs.columbia.edu/manager -Djava.security.policy=%PSLROOT%\psl\wgcache\pcm.policy -classpath .:%PSLROOT%:%PSLROOT%\psl\jars\siena-1.1.2.jar:%PSLROOT%\psl\jars\hsql.jar psl.wgcache.PCM_Visual
