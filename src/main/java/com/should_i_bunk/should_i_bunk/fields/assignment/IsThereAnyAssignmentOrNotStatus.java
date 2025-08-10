package com.should_i_bunk.should_i_bunk.fields.assignment;

import lombok.Getter;

@Getter
public enum IsThereAnyAssignmentOrNotStatus {
    YES("Yes we have assignment"), NO("No we don't have assignment"),;

    private final String AssignmentStatus;
    IsThereAnyAssignmentOrNotStatus(final String AssignmentStatus) {
        this.AssignmentStatus = AssignmentStatus;
    }

}
