package com.artemf29.core.util;

import com.artemf29.core.model.Restaurant;
import com.artemf29.core.model.User;
import com.artemf29.core.model.Vote;
import com.artemf29.core.to.VoteTo;
import com.artemf29.core.util.exception.UpdateVoteException;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.artemf29.core.web.ExceptionInfoHandler.EXCEPTION_UPDATE_VOTE;

public class VoteUtil {
    public static void reVotingPermission() {
        if (LocalTime.now().isAfter(LocalTime.of(11, 0)))
            throw new UpdateVoteException(EXCEPTION_UPDATE_VOTE);
    }

    public static VoteTo createTo(Vote vote) {
        User user = vote.getUser();
        Restaurant rest = vote.getRestaurant();
        return new VoteTo(vote.getId(), vote.getVoteDate(), user.getId(), rest.getId());
    }

    public static List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::createTo)
                .collect(Collectors.toList());
    }
}