package adventofcode.day3;

import adventofcode.utill.InputReader;
import java.util.*;

public class Day3Part2 {
    private Map<Integer, List<Integer>> findGearConnections(List<String> schematic){
        Map<Integer, List<Integer>> values = new HashMap<>();
        int index = 0;
        for (String schematicLine:schematic) {
            Optional<Integer> gearIndex = Optional.empty();
            StringBuilder valueInProgress = new StringBuilder();
            for (char value: schematicLine.toCharArray()) {
                if(Character.isDigit(value)){
                    gearIndex = gearIndex.isPresent() ? gearIndex : isIndexValid(index, schematic);
                    valueInProgress.append(value);
                } else {
                    if(valueInProgress.length() > 0 && gearIndex.isPresent()){
                        addGearIndexValue(gearIndex.get(),Integer.valueOf(valueInProgress.toString()), values);
                        valueInProgress.setLength(0);
                        gearIndex = Optional.empty();
                    } else {
                        valueInProgress.setLength(0);
                    }
                }
                index++;
            }
            if(valueInProgress.length() > 0 && gearIndex.isPresent()){
                addGearIndexValue(gearIndex.get(),Integer.valueOf(valueInProgress.toString()), values);
            }
        }
        return values;
    }

    private void addGearIndexValue(Integer gearIndex, Integer value, Map<Integer, List<Integer>> values) {
        values.computeIfAbsent(gearIndex, k -> new ArrayList<>()).add(value);
    }

    private Optional<Integer> isIndexValid (int index, List<String> schematic){
        int schematicLength = schematic.get(0).length();
        int row = index % schematicLength;
        int column = index / schematicLength;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int columnIndex = Math.min(Math.max(column + i, 0), schematic.size() - 1);
                int rowIndex = Math.min(Math.max(row + j, 0),schematicLength - 1);
                if(isSpecial(schematic.get(columnIndex).charAt(rowIndex))){
                    return Optional.of(columnIndex * schematic.size() + rowIndex);
                }
            }
        }
        return Optional.empty();
    }

    private boolean isSpecial(char value){
        return !Character.isDigit(value) && value == '*';
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day3/input.txt", this.getClass());

        int result = findGearConnections(input).values().stream().map(values -> {
            if(values.size() == 2){
                return values.get(0) * values.get(1);
            }
            return 0;
        }).reduce(0, Integer::sum);

//        System.out.println(findGearConnections(input));

        System.out.println("Result of day 3 part 1: " + result);
    }

    public static void main(String[] args) {
        Day3Part2 day3Part2 = new Day3Part2();
        day3Part2.findResult();
    }
}
