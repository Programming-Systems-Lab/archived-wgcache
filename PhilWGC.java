package psl.wgcache;

/**
 *  Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *   
 * @author  Alpa
 *  
 */

public class PhilWGC {
  public static void main(String[] args) {
    System.out.println("Phil's personal cache system: an innocent bystander");
    PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Phil");
    
    try {
      pcm.joinWorkgroup("PSL-Workgroup");
      System.out.println("Phil joined existing workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Phil failed to join workgroup: \"PSL-Workgroup\"");
    }

  }
}
