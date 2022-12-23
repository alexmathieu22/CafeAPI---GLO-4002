package ca.ulaval.glo4002.cafe.domain.valueobjects;

public record CafeName(String value) {
    public CafeName {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Cafe name cannot be empty.");
        }
    }
}
