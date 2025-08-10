package com.should_i_bunk.should_i_bunk.fields.interestInSubject;

import lombok.Getter;

@Getter
public enum InterestInSubjectStatusOutOfFive {
    NOT_INTERESTED(1),
    SOMEWHAT_INTERESTED(2),
    NEUTRAL(3),
    INTERESTED(4),
    VERY_INTERESTED(5);

    private final int value;

    InterestInSubjectStatusOutOfFive(int value) {
        this.value = value;
    }

}
