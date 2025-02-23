package dh.backend.music_store.dto.product;

import dh.backend.music_store.utils.GsonProvider;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindAllProductRequestDto {
    private String search = null;
    private Integer categoryId = null;
    private Integer limit = 20;
    private Integer page = 1;

    @Override
    public String toString() {
        return GsonProvider.getGson().toJson(this);
    }
}
