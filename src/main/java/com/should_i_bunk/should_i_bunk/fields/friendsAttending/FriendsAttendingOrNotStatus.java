package com.should_i_bunk.should_i_bunk.fields.friendsAttending;

import lombok.Getter;

@Getter
public enum FriendsAttendingOrNotStatus {
    YES("Yes"),
    NO("No");

    private final String friendsAttendingOrNotStatus;

    FriendsAttendingOrNotStatus(String friendsAttendingOrNotStatus) {
        this.friendsAttendingOrNotStatus = friendsAttendingOrNotStatus;
    }
}
