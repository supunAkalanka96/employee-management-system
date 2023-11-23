package com.application.security.dto;

import com.application.security.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class UserDTO {
    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private int roleId;
}
