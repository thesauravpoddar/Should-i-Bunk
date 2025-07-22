package com.should_i_bunk.should_i_bunk.exceptionHandling;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
   private final ErrorCode errorCode;
   private final Object[] args;

   public BusinessException(final ErrorCode errorCode, final Object... args) {
      super(getFormatterMessage(errorCode, args));
      this.errorCode = errorCode;
      this.args = args;
   }

   private static String getFormatterMessage(ErrorCode errorCode, Object[] args) {
        String message = errorCode.getDefaultMessage();
        if (args != null && args.length > 0) {
             return String.format(message, args);
        }
        return message;
   }


}
