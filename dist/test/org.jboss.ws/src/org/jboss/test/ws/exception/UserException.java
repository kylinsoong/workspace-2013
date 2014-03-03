package org.jboss.test.ws.exception;

import javax.xml.ws.WebFault;

/**
 * A mach application exception
 *
 * @author <a href="jason.greene@jboss.com">Jason T. Greene</a>
 */
@WebFault(faultBean="org.jboss.test.ws.exception.jaxws.UserExceptionBean")
public class UserException extends Exception
{
   private int errorCode;
   private String errorCategory;

   public UserException(String errorCategory, int errorCode, String message)
   {
      super(message);

      this.errorCategory = errorCategory;
      this.errorCode = errorCode;
   }

   public String getErrorCategory()
   {
      return errorCategory;
   }

   public int getErrorCode()
   {
      return errorCode;
   }
}
