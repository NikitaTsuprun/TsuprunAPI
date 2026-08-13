package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    RUB("RUB"),
    EUR("EUR"),
    USD("USD");

    private final String code;
}
