package psl.wgcache.exception;

public class NoSuchModuleException
  extends WGCException
{
  public NoSuchModuleException(String mod)
  {
    super("No such module: \"" + mod + "\"");
  }
}
