echo Executing Workgroup Cache - the Workgroup Manager

java -DSpecfile=%PSLROOT%\psl\xues\WGCRules.xml -classpath .:%PSLROOT%:%PSLROOT%\psl\jars\siena-1.1.2.jar:%PSLROOT%\psl\jars\xerces.jar psl.wgcache.WGC_Manager_Visual
