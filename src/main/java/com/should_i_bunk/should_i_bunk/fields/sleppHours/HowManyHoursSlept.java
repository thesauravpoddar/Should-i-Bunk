package com.should_i_bunk.should_i_bunk.fields.sleppHours;

import lombok.Getter;

@Getter
public enum HowManyHoursSlept {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THIRTEEN(13),;

    private final int horsSlept;

    HowManyHoursSlept(final int hoursSlept) {
        this.horsSlept = hoursSlept;
    }

    public static HowManyHoursSlept getHowManyHoursSlept(final int hoursSlept) {
        for(HowManyHoursSlept howManyHoursSlept : HowManyHoursSlept.values()) {
            if(hoursSlept == howManyHoursSlept.getHorsSlept()) {
                return howManyHoursSlept;
            }
        }
        throw new IllegalArgumentException("Invalid hours slept: " + hoursSlept);
    }

}
