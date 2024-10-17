package oince.projecthd.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public enum CategoryName {
    FOOD, SW, PC, ELECTRONIC, LIVING, CLOTHES, SALES, BEAUTY, MOBILE, PACKAGE, ETC;

    @JsonCreator
    public static CategoryName fromString(String name) {
        for (CategoryName value : CategoryName.values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }
        log.warn("CategoryName parsing failed");
        throw new IllegalArgumentException("Invalid category name: " + name);
    }
}
