package com.should_i_bunk.should_i_bunk.fields.examSoon;

import lombok.Getter;

@Getter
public enum ExamSoonStatus {
    YES("Exam is coming soon!"),
    NO("No exams coming soon!"),;
    private final String examNearStatus;

    ExamSoonStatus(final String examNearStatus) {
        this.examNearStatus = examNearStatus;
    }
}
