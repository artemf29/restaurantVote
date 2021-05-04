package com.artemf29.core.testData;

import com.artemf29.core.TestMatcher;
import com.artemf29.core.model.Vote;
import com.artemf29.core.to.VoteTo;

import java.time.Month;
import java.util.List;

import static com.artemf29.core.testData.RestaurantTestData.*;
import static com.artemf29.core.testData.UserTestData.admin;
import static com.artemf29.core.testData.UserTestData.user;
import static com.artemf29.core.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDate.*;

public class VoteTestData {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingEqualsComparator(VoteTo.class);

    public static final int NOT_FOUND = 102;
    public static final int VOTE_1_ID = START_SEQ + 11;
    public static final int VOTE_2_ID = START_SEQ + 12;
    public static final int VOTE_3_ID = START_SEQ + 13;
    public static final int VOTE_4_ID = START_SEQ + 14;

    public static final Vote vote1 = new Vote(VOTE_1_ID, of(2021, Month.APRIL, 28));
    public static final Vote vote2 = new Vote(VOTE_2_ID, of(2021, Month.APRIL, 28));
    public static final Vote vote3 = new Vote(VOTE_3_ID, of(2021, Month.MARCH, 8));
    public static final Vote vote4 = new Vote(VOTE_4_ID, of(2021, Month.MARCH, 8));

    static {
        vote1.setUser(user);
        vote1.setRestaurant(restaurant1);
        vote2.setUser(admin);
        vote2.setRestaurant(restaurant2);
        vote3.setUser(user);
        vote3.setRestaurant(restaurant1);
        vote4.setUser(admin);
        vote4.setRestaurant(restaurant3);
    }

    public static final List<Vote> voteUser = List.of(vote1, vote3);

    public static Vote getNew() {
        Vote vote = new Vote(null, now());
        vote.setUser(user);
        vote.setRestaurant(restaurant3);
        return vote;
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE_1_ID, now());
        vote.setUser(vote.getUser());
        vote.setRestaurant(restaurant2);
        return vote;
    }
}
