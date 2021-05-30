package com.artemf29.core.util;

import com.artemf29.core.model.Restaurant;
import com.artemf29.core.model.Vote;
import com.artemf29.core.to.VoteTo;
import com.artemf29.core.util.exception.UpdateVoteException;

import java.time.LocalTime;

import static com.artemf29.core.web.ExceptionInfoHandler.EXCEPTION_UPDATE_VOTE;

public class VoteUtil {
    private static final LocalTime UPDATE_DEADLINE = LocalTime.of(11, 0);

    public static void reVotingPermission() {
        if (LocalTime.now().isAfter(UPDATE_DEADLINE))
            throw new UpdateVoteException(EXCEPTION_UPDATE_VOTE);
    }

    public static VoteTo createTo(Vote vote) {
        Restaurant rest = vote.getRestaurant();
        return new VoteTo(vote.getId(), vote.getVoteDate(), rest.getId(), rest.getName());
    }
}