package dh.backend.music_store.dto.user.response;

import dh.backend.music_store.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindByEmailResponseDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneCode;

    private String phone;

    private String address;

    private String role;
}
