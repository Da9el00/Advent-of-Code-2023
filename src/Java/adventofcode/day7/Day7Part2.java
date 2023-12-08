package adventofcode.day7;

import adventofcode.utill.InputReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7Part2 {

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day7/input.txt", this.getClass());
        Map<String, String> cardsSets = input.stream()
                .map(cardInfo -> cardInfo.split(" "))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));

        CardSetComparatorJoker cardSetComparator = new CardSetComparatorJoker();
        List<Map.Entry<String, String>> sortedMap = cardsSets.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(cardSetComparator)).collect(Collectors.toList());

        System.out.println(sortedMap);

        int result = 0;

        for (int i = 0; i < sortedMap.size(); i++) {
            result += Integer.parseInt(sortedMap.get(i).getValue()) * (i + 1);
        }

        System.out.println("Result of day 7 part 2: " + result);
    }

    public static void main(String[] args) {
        Day7Part2 day7Part1 = new Day7Part2();
        day7Part1.findResult();
    }
}