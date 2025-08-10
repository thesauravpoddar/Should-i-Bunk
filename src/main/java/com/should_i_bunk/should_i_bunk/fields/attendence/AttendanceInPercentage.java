package com.should_i_bunk.should_i_bunk.fields.attendence;

import lombok.Getter;

@Getter
public enum AttendanceInPercentage {
    BELOW_50(0 , 49),
    Between_50_and_74(50, 74),
    Between_75_and_89(75, 89),
    Between_90_and_100(90, 100);

    private final int min;
    private final int max;

    AttendanceInPercentage(final int min , final int max) {
        this.min = min;
        this.max = max;
    }

    public static AttendanceInPercentage getAttendanceInPercentage(final double percentage) {
        for(AttendanceInPercentage attendanceInPercentage : AttendanceInPercentage.values()) {
            if(percentage >= attendanceInPercentage .getMin() && percentage <= attendanceInPercentage.getMax()) {
                return attendanceInPercentage;
            }
        }
        throw new IllegalArgumentException("Invalid percentage: " + percentage);
    }

}
