package vudovenko.dev.patterns;

import vudovenko.dev.patterns.facade.PollLifecycleFacade;
import vudovenko.dev.patterns.facade.impl.PollLifecycleFacadeImpl;
import vudovenko.dev.patterns.poll.models.Poll;
import vudovenko.dev.patterns.poll.models.PollFillingData;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PollLifecycleFacade pollLifecycleFacade = new PollLifecycleFacadeImpl();
        Poll poll = pollLifecycleFacade.createPoll();
        List<PollFillingData> pollFillingDataList = pollLifecycleFacade.getUserResponses(poll);

        pollLifecycleFacade.makeAnalyzePoll(pollFillingDataList);
    }
}
