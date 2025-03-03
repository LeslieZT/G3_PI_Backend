package dh.backend.music_store.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllUserResponseDto {
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private RoleResponseDto role;
}
