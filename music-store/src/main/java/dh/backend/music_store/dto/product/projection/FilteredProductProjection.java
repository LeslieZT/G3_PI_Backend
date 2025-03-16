package dh.backend.music_store.dto.product.projection;

public interface FilteredProductProjection {
    Integer getId();
    String getName();
    String getUrl();
    Double getPricePerHour();
    String getCategoryName();
    String getDescription();

}
