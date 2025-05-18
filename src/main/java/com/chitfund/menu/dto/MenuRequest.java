package com.chitfund.menu.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MenuRequest {
  @NotBlank(message = "Menu name is required")
  private String menuName;
  @NotBlank(message = "Menu type is required")
  private String menuType;
  @NotBlank(message = "Menu order is required")
  private String menuOrder;
  @NotBlank(message = "Menu URL is required")

  private String menuUrl;
  private String menuIcon;
  private List<MenuRequest> subMenu;
  private boolean checked;
  private boolean indeterminate;
  private boolean unselectable;
}
