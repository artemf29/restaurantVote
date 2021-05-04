package com.artemf29.core.web.vote;

import com.artemf29.core.repository.VoteRepository;
import com.artemf29.core.to.VoteTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.artemf29.core.util.ValidationUtil.checkSingleModification;
import static com.artemf29.core.util.VoteUtil.createTo;
import static com.artemf29.core.util.VoteUtil.getTos;

@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/admin/vote";

    protected VoteRepository voteRepository;

    public AdminVoteRestController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/{userId}/{id}")
    public ResponseEntity<VoteTo> get(@PathVariable int userId, @PathVariable int id) {
        log.info("get {} for User{}", id, userId);
        return ResponseEntity.of(Optional.of(createTo(voteRepository.get(id, userId).get())));
    }

    @DeleteMapping("/{userId}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int userId, @PathVariable int id) {
        log.info("delete {} for User {}", id, userId);
        checkSingleModification(voteRepository.delete(id, userId), "Vote id=" + id + ", User id=" + userId + " missed");
    }

    @GetMapping("/getAll/{id}")
    public List<VoteTo> getAllByUser(@PathVariable int id) {
        log.info("getAll by User {}", id);
        return getTos(voteRepository.getAllByUser(id));
    }

    @GetMapping("/getAll")
    public List<VoteTo> getAll() {
        log.info("getAll");
        return getTos(voteRepository.getAll());
    }
}
