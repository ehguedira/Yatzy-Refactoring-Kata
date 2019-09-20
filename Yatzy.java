package com.carrefour.one.publication.middlepub.rest;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Yatzy {

    private final int[] dice;

    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        // dice = new int[]{d1, d2, d3, d4, d5};
        dice = Stream.of(d1, d2, d3, d4, d5)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static int chance(int d1, int d2, int d3, int d4, int d5) {
        // return d1 + d2 + d3 + d4 + d5;
        return Stream.of(d1, d2, d3, d4, d5)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int yatzy(int... dice) {
        int[] counts = new int[6];
        IntStream.of(dice).forEach(die -> counts[die - 1]++);

        return IntStream.range(0, counts.length)
                .filter(value -> counts[value] == 5)
                .map(operand -> 50)
                .findFirst().orElse(0);

    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return IntStream.of(d1, d2, d3, d4, d5)
                .filter(value -> value == 1)
                .sum();
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return IntStream.of(d1, d2, d3, d4, d5)
                .filter(value -> value == 2)
                .sum();
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return IntStream.of(d1, d2, d3, d4, d5)
                .filter(value -> value == 3)
                .sum();
    }

    public static int scorePair(int d1, int d2, int d3, int d4, int d5) {
        int[] counts = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> counts[value - 1]++);

        return IntStream.range(0, counts.length)
                .filter(value -> counts[6 - value - 1] >= 2)
                .map(value -> (6 - value) * 2)
                .findFirst().orElse(0);
    }

    public static int twoPair(int d1, int d2, int d3, int d4, int d5) {
        int[] counts = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> counts[value - 1]++);

        int n = 0;
        int score = 0;
        for (int i = 0; i < 6; i += 1)
            if (counts[6 - i - 1] >= 2) {
                n++;
                score += (6 - i);
            }
        if (n == 2)
            return score * 2;
        else
            return 0;
    }

    public static int fourOfAKind(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);

        return IntStream.range(0, tallies.length)
                .filter(value -> tallies[value] >= 4)
                .map(value -> (value + 1) * 4)
                .findFirst().orElse(0);
    }

    public static int threeOfAKind(int d1, int d2, int d3, int d4, int d5) {
        int[] t = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> t[value - 1]++);

        return IntStream.range(0, t.length)
                .filter(value -> t[value] >= 3)
                .map(value -> (value + 1) * 3)
                .findFirst().orElse(0);
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);

        return IntStream.of(tallies)
                .limit(5)
                .allMatch(value -> value == 1) ? 15 : 0;

    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5) {

        int[] tallies = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);

        return IntStream.of(tallies)
                .skip(1)
                .allMatch(value -> value == 1) ? 20 : 0;

    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
        int[] tallies = new int[6];
        IntStream.of(d1, d2, d3, d4, d5).forEach(value -> tallies[value - 1]++);
        OptionalInt b2At = IntStream.range(0, 6).filter(value -> tallies[value] == 2).reduce((first, second) -> second);
        OptionalInt b3At = IntStream.range(0, 6).filter(value -> tallies[value] == 3).reduce((first, second) -> second);

        return b2At.isPresent() && b3At.isPresent() ? (b2At.getAsInt()+1) * 2 + (b3At.getAsInt()+1) * 3 : 0;
    }

    public int fours() {
        return IntStream.range(0, 4)
                .filter(value -> dice[value] == 4)
                .reduce(0, (x, y) -> x + 4);

    }

    public int fives() {

        return IntStream.range(0, dice.length)
                .filter(value -> dice[value] == 5)
                .reduce(0, (x, y) -> x + 5);
    }

    public int sixes() {
        return IntStream.range(0, dice.length)
                .filter(value -> dice[value] == 6)
                .reduce(0, (x, y) -> x + 6);
    }

}