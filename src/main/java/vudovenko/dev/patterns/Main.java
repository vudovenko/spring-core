package vudovenko.dev.patterns;

import vudovenko.dev.patterns.poll.models.Poll;

public class Main {
    public static void main(String[] args) {
        Poll poll = Poll.builder()
                .withPollName("Programming Survey")
                .yesNoPollQuestion("Are you programmer?")

                .oneVariantPollQuestion("How many years of programming experience do you have?")
                .withAnswerVariant("0-1 years")
                .withAnswerVariant("1-3 years")
                .withAnswerVariant("3-5 years")
                .withAnswerVariant("5+ years")
                .and()
                .oneVariantPollQuestion("What is your favorite programming language?")
                .withAnswerVariant("Java")
                .withAnswerVariant("Python")
                .withAnswerVariant("C++")
                .and()
                .optionalResponsesPollQuestion("Do you like your job?")
                .withMaxAnswers(1)
                .withAnswerVariant("Who knows...")
                .withAnswerVariant("Of course")
                .and()
                .pollQuestion("What are your strong qualities?")
                .withMinAnswers(0)
                .withMaxAnswers(5)
                .withAnswerVariant("Leadership")
                .withAnswerVariant("Teamwork")
                .withAnswerVariant("Problem-solving")
                .withAnswerVariant("Communication")
                .withAnswerVariant("Creativity")
                .and()
                .build();
    }
}