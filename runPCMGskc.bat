echo Executing Workgroup Cache - the Personal Cache Module
java -DUser=Gskc -DManager=rmi://varick/manager -Djava.security.policy=c:\pslcvs\psl\wgcache\pcm.policy -classpath c:\pslcvs\jars\wgc.jar;c:\pslcvs\jars\hsql.jar psl.wgcache.PCM_Visual
