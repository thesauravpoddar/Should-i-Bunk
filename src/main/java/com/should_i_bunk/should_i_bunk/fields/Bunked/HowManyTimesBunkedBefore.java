package com.should_i_bunk.should_i_bunk.fields.Bunked;

import lombok.Getter;

@Getter
public enum HowManyTimesBunkedBefore {
    ONE_TIME("one time"),
    TWO_TIMES("Two times"),
    THREE_TIMES("Three Times"),
    FOUR_TIMES("Four Times"),
    FIVE_TIMES("Five Times"),
    SIX_TIMES("Six Times"),
    SEVEN_TIMES("Seven Times"),
    EIGHT_TIMES("Eight Times"),
    NINE_TIMES("Nine Times"),
    TEN_OR_MORE("Ten or More");

    private final String description;

    HowManyTimesBunkedBefore(String description) {
        this.description = description;
    }

}
