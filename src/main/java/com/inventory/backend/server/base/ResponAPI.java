package com.inventory.backend.server.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponAPI<T> {
  private String errorMessage;
  private String errorCode;
  private T data;
}
