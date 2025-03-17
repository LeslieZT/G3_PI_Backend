package dh.backend.music_store.entity;

public enum ReservationStatus {
    PENDING,      // Waiting for approval
    APPROVED,     // Approved for loan
    IN_PROGRESS,  // Instrument is currently loaned out
    RETURNED,     // Instrument has been returned
    CANCELED      // Reservation was canceled
}
