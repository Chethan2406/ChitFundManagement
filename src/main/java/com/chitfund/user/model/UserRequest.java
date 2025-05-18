package com.chitfund.user.model;
import lombok.*;
import javax.validation.constraints.*;

import com.chitfund.menu.model.MenuInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNo;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Status is required")
    private Integer status;

    @NotNull(message = "Role ID is required")
    private Role roleId;

    private Boolean deleteFlag = false;

    private List<MenuInfo> menuInfo;
}

