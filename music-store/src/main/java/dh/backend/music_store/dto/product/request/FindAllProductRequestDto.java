package dh.backend.music_store.dto.product.request;

import dh.backend.music_store.utils.GsonProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductRequestDto {
    @Size(min = 3, message = "Search term must have at least 3 characters.")
    private String search;

    @Size(min = 1, message = "There must be at least one role ID in the list.")
    private List< @Valid Integer> categories;

    @Min(value = 1, message = "Limit must be at least 1.")
    private Integer limit = 20;

    @Min(value = 1, message = "Page must be at least 1.")
    private Integer page = 1;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
