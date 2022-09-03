package lesson46;

public enum BookState {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available");

    private String value;

    BookState(String value) {
        this.value = value;
    }
}
