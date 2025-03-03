package dh.backend.music_store.dto.user.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dh.backend.music_store.utils.GsonProvider;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllUserRequestDto {

    @Size(min = 3, message = "Search term must have at least 3 characters.")
    private String search;

    @Size(min = 1, message = "There must be at least one role ID in the list.")
    private List< @Valid Integer> roles;

    @Min(value = 1, message = "Limit must be at least 1.")
    private Integer limit = 20;

    @Min(value = 1, message = "Page must be at least 1.")
    private Integer page = 1;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
