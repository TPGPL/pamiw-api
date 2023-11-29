package pl.edu.pw.pamiwapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @NotNull(message = "The username must not be null.")
    @Size(min = 3, max = 50, message = "The username must be between 3 and 50 characters.")
    private String username;
    @NotNull(message = "The email must not be null.")
    @Email(message = "The email must be valid.")
    @Size(min = 3, max = 50, message = "The username must be between 3 and 50 characters.")
    private String email;
    @NotNull(message = "The password must not be null.")
    @Size(min = 8, max = 30, message = "The password must be between 8 and 30 characters.")
    private String password;
}
