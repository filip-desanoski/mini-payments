package dev.desan.minipayments.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndPoints {
    public static final String BASE = "/api";
    public static final String CUSTOMER = BASE + "/customers";
    public static final String PAYMENT = BASE + "/payments";
    public static final String LOCATION = BASE + "/locations";
}
