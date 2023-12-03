package adventofcode.day2;

import adventofcode.utill.InputReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day2Part1 {
    private final Map<String, Integer> maxCubeColorMap = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14);

    public boolean isRecordValid(String record) {
        List<String> setsOfCubes = List.of(record.split(":")[1].split(";"));

        return setsOfCubes.stream().allMatch(setOfCubes ->
                Stream.of(setOfCubes.split(","))
                        .allMatch(cubeData -> {
                            String[] cubeDataParts = cubeData.trim().split(" ");
                            int amount = Integer.parseInt(cubeDataParts[0]);
                            String color = cubeDataParts[1];
                            int maxAmount = maxCubeColorMap.get(color);

                            return amount <= maxAmount;
                        })
        );
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day2/input.txt", this.getClass());

        int result = input.stream()
                .filter(this::isRecordValid)
                .map(record -> Integer.parseInt(record.split(":")[0]
                .split(" ")[1]))
                .reduce(0, Integer::sum);
        System.out.println("Result of day 2 part 1: " + result);
    }

    public static void main(String[] args) {
        Day2Part1 day2Part1 = new Day2Part1();
//        System.out.println(day2Part1.isRecordValid("Game 1: 3 blue, 4 red; 1 red, 22 green, 6 blue; 2 green"));

        day2Part1.findResult();
    }
}
