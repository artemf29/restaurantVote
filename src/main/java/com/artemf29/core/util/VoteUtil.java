package com.artemf29.core.util;

import com.artemf29.core.util.exception.UpdateVoteException;

import java.time.LocalTime;

import static com.artemf29.core.web.ExceptionInfoHandler.EXCEPTION_UPDATE_VOTE;

public class VoteUtil {
    private static final LocalTime UPDATE_DEADLINE = LocalTime.of(11, 0);

    public static void reVotingPermission() {
        if (LocalTime.now().isAfter(UPDATE_DEADLINE))
            throw new UpdateVoteException(EXCEPTION_UPDATE_VOTE);
    }
}