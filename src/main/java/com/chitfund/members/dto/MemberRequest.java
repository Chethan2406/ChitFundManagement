package com.chitfund.members.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class MemberRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Aadhar number is required")
    @Pattern(regexp = "^\\d{12}$", message = "Aadhar number must be exactly 12 digits")
    private String aadharNumber;

    @NotBlank(message = "Age is required")
    @Pattern(regexp = "^(1[89]|[2-9]\\d)$", message = "Age must be a valid number 18 or above")
    private String age;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @NotBlank(message = "KYC status is required")
    @Pattern(regexp = "^(Pending|Approved|Rejected)$", message = "KYC status must be Pending, Approved, or Rejected")
    private String kycStatus;

    @NotNull(message = "Created date is required")
    private LocalDate createdDate;

    private LocalDate updatedDate;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(Active|Inactive|Suspended)$", message = "Status must be Active, Inactive, or Suspended")
    private String status;
}
