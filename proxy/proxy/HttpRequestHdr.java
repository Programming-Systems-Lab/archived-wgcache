/******************************************************************
*** File HttpRequestHdr.java 
***
***/

import java.io.InputStream;
import java.io.DataInputStream;
import java.util.StringTokenizer;

// 
// Class:     HttpRequestHdr
// Abstract:  The headers of the client HTTP request.
//

public class HttpRequestHdr {
   * hopefully uniquely describes the object or service the
   * client is requesting. 
   */
   public String url   = new String();
   * Version of http being used. Such as HTTP/1.0
   */
   * The client's browser's name.
   */
   public String userAgent         = new String();

   /**
    * The requesting documents that contained the url link.
    */
   public String referer           = new String();

   /**
    * A internet address date of the remote copy.
    */
   public String ifModifiedSince   = new String();

   /**
    * A list of mime types the client can accept.
    */
   public String accept            = new String();

   /**
    * The clients authorization. Don't belive it.
    */

   public String authorization     = new String();
   /**
    * The type of content following the request header.
    * Normally there is no content and this is blank, however
    * the post method usually does have a content and a content 
    * length.
    */
   public String contentType       = new String();
   /**
    * The length of the content following the header. Usually
    * blank.
    */
   public int    contentLength     = -1;
   /**
    * The content length of a remote copy of the requested object.
    */
   public int    oldContentLength  = -1;
   /**
    * Anything in the header that was unrecognized by this class.
    */
   public String unrecognized      = new String();
   /**
    * Indicates that no cached versions of the requested object are
    * to be sent. Usually used to tell proxy not to send a cached copy.
    * This may also effect servers that are front end for data bases.
    */
   public boolean pragmaNoCache    = false;

   static String CR ="\r\n";


/**
 * Parses a http header from a stream.
 *
 * @param in  The stream to parse. 
 * @return    true if parsing sucsessfull.
 */ 
    String CR ="\r\n";
    StringTokenizer tz;
    try {
      lines = new DataInputStream(In);
      tz = new StringTokenizer(lines.readLine());
    } catch (Exception e) {
    /*
    * HTTP COMMAND LINE < <METHOD==get> <URL> <HTTP_VERSION> >
    */
    method = getToken(tz).toUpperCase();
    url    = getToken(tz);
    version= getToken(tz);
                       
    while (true) {
      try {
        tz = new StringTokenizer(lines.readLine());
      } catch (Exception e) {
      // look for termination of HTTP command
      if (0 == Token.length())
      break;
                     
      if (Token.equalsIgnoreCase("USER-AGENT:")) {
        // line =<User-Agent: <Agent Description>>
        int index = str.indexOf(";");
        if (index == -1) {
          ifModifiedSince  =str;
        } else {
          ifModifiedSince  =str.substring(0,index);
          index = str.indexOf("=");
   /*
    * Rebuilds the header in a string
    * @returns      The header in a string.
    */

      Request +="User-Agent:" + userAgent + CR;     

    if (pragmaNoCache)
      // ACCEPT TYPES //
    if (0 < accept.length())
      Request += "Accept: " + accept + CR;
    else 
      Request += "Accept: */"+"* \r\n";
              
    if (0 < contentType.length())
      Request += "Content-Type: " + contentType   + CR;

    if (0 < contentLength)
      Request += "Content-Length: " + contentLength + CR;
        Request += unrecognized;
    }
    Request += CR;
    return Request;
   * @returns      The header in a string.
   */
   *  Returns the next token in a string
   *
   * @param   tk String that is partially tokenized.
   * @returns The remainder
  /**
   *
   * @param   tk String that is partially tokenized.
   * @returns The remainder
   */
      str =tk.nextToken();
    while (tk.hasMoreTokens()){
      str +=" " + tk.nextToken();
    }
    return str;