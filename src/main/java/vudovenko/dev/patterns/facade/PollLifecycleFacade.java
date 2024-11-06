package vudovenko.dev.patterns.facade;

import vudovenko.dev.patterns.poll.models.Poll;
import vudovenko.dev.patterns.poll.models.PollFillingData;

import java.util.List;

public interface PollLifecycleFacade {

    Poll createPoll();

    List<PollFillingData> getUserResponses(Poll poll);

    void makeAnalyzePoll(List<PollFillingData> pollFillingDataList);
}
