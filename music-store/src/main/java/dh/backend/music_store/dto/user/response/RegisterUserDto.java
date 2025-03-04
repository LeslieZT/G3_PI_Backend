package dh.backend.music_store.dto.user.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String message;

}
