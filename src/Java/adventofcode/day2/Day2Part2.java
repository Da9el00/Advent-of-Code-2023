package adventofcode.day2;

import adventofcode.utill.InputReader;

import java.util.List;

public class Day2Part2 {
    public int findMaxValuesForRecord(String record) {
        int maxRed = 0;
        int maxBlue = 0;
        int maxGreen = 0;

        List<String> setsOfCubes = List.of(record.split(":")[1].split(";"));

        for (String cubeSet: setsOfCubes) {
            String[] cubeData = cubeSet.split(",");
            for (String cube: cubeData) {
                String[] cubeDataParts = cube.trim().split(" ");
                int amount = Integer.parseInt(cubeDataParts[0]);
                String color = cubeDataParts[1];

                switch (color){
                    case "blue"  -> maxBlue = Math.max(maxBlue, amount);
                    case "red"  -> maxRed = Math.max(maxRed, amount);
                    case "green"  -> maxGreen = Math.max(maxGreen, amount);
                }
            }
        }
        return maxRed * maxGreen * maxBlue;
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day2/input.txt", this.getClass());

        int result = input.stream()
                .map(this::findMaxValuesForRecord)
                .reduce(0, Integer::sum);

        System.out.println("Result of day 2 part 2: " + result);
    }

    public static void main(String[] args) {
        Day2Part2 day2Part2 = new Day2Part2();

        day2Part2.findResult();
    }
}
