package org.user.application.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {
   private String message;
}
