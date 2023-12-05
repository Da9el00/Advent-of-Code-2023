package adventofcode.day4;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;
import java.util.stream.IntStream;

public class Day4Part2 {

    private final Queue<Integer> cardsToBeProcessed = new PriorityQueue<>();
    private int cardsVisited = 0;
    private final Day4Part1 day4Part1 = new Day4Part1();
    private final List<String> cards = InputReader.readInputByLine("adventofcode/day4/input.txt", this.getClass());

    private final Map<Integer, List<Integer>> cardMemory = new HashMap<>();

    private List<Integer> findNextCards(int cardNumber, long winners){
        return IntStream.range(cardNumber + 1, (int) (cardNumber + winners + 1))
                .boxed()
                .toList();
    }

    public void findCardsForCardsWhile(Queue<Integer> cardsToBeProcessed, boolean withMemory){
        while (!cardsToBeProcessed.isEmpty()){
            int indexOfCurrentCard = cardsToBeProcessed.poll();

            if(cardMemory.containsKey(indexOfCurrentCard) && withMemory){
                List<Integer> cardsToVisit = cardMemory.get(indexOfCurrentCard);
                cardsToBeProcessed.addAll(cardsToVisit);
            } else {
                long winningNumbers = day4Part1.findWinningNumbers(cards.get(indexOfCurrentCard));
                List<Integer> cardsToVisit = findNextCards(indexOfCurrentCard, winningNumbers);
                cardMemory.put(indexOfCurrentCard, cardsToVisit);
                cardsToBeProcessed.addAll(cardsToVisit);
            }

            //Keep track of how many cards was visited
            cardsVisited++;
        }
    }

    public void findResult(boolean withMemory){
        cardsToBeProcessed.addAll(IntStream.range(0 , cards.size())
                .boxed()
                .toList());

        findCardsForCardsWhile(cardsToBeProcessed, withMemory);

        System.out.println("Result of day 4 part 1: " + cardsVisited);
    }

    public static void main(String[] args) {
        Day4Part2 day4Part2 = new Day4Part2();

        Timer.timeChecker(() -> day4Part2.findResult(true));

        //Result of day 4 part 1: 19499881
        //Time: 43 seconds
    }
}
