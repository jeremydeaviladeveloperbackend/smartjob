package org.smartjob.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String id;
    private String created;
    private String modified;

    @JsonProperty("last_login")
    private String lastLogin;

    private String token;

    @JsonProperty("isactive")
    private String isActive;
}
