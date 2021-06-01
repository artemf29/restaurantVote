package com.artemf29.core.testdata;

import com.artemf29.core.TestMatcher;
import com.artemf29.core.model.Vote;
import com.artemf29.core.to.VoteTo;

import java.time.Month;
import java.util.List;

import static com.artemf29.core.testdata.RestaurantTestDataUtils.*;
import static com.artemf29.core.testdata.UserTestDataUtils.admin;
import static com.artemf29.core.testdata.UserTestDataUtils.user;
import static com.artemf29.core.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDate.*;

public class VoteTestDataUtils {
    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingEqualsComparator(VoteTo.class);

    public static final int NOT_FOUND = 102;
    public static final int VOTE_1_ID = START_SEQ + 17;
    public static final int VOTE_2_ID = START_SEQ + 18;
    public static final int VOTE_3_ID = START_SEQ + 19;

    public static final Vote vote1 = new Vote(VOTE_1_ID, now());
    public static final Vote vote2 = new Vote(VOTE_2_ID, of(2021, Month.MARCH, 8));
    public static final Vote vote3 = new Vote(VOTE_3_ID, of(2021, Month.MARCH, 8));

    static {
        vote1.setUser(admin);
        vote1.setRestaurant(restaurant2);
        vote2.setUser(user);
        vote2.setRestaurant(restaurant1);
        vote3.setUser(admin);
        vote3.setRestaurant(restaurant3);
    }

    public static Vote getNew() {
        Vote vote = new Vote(null, now());
        vote.setUser(user);
        vote.setRestaurant(restaurant3);
        return vote;
    }

    public static Vote getUpdated() {
        Vote vote = new Vote(VOTE_1_ID, now());
        vote.setUser(vote.getUser());
        vote.setRestaurant(restaurant3);
        return vote;
    }
}
