package dh.backend.music_store.dto.user.projection;

public interface FilteredUserProjection {
    Integer getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Integer getRoleId();
    String getRoleName();
}
