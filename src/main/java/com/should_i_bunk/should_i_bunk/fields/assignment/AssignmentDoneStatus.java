package com.should_i_bunk.should_i_bunk.fields.assignment;

import lombok.Getter;

@Getter
public enum AssignmentDoneStatus {
    YES("Yes Done") , NOT_YET("Not Done"),;

    private final String status;
    AssignmentDoneStatus(final String status) {
        this.status = status;
    }

}
