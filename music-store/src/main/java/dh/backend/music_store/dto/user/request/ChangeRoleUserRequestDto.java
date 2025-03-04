package dh.backend.music_store.dto.user.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRoleUserRequestDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Integer userId;

    @NotNull(message = "Role ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Integer roleId;


}
