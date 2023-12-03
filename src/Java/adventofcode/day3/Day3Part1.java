package adventofcode.day3;

import adventofcode.utill.InputReader;

import java.util.ArrayList;
import java.util.List;


public class Day3Part1 {

    private List<Integer> findValue(List<String> schematic){
        List<Integer> values = new ArrayList<>();
        int index = 0;
        for (String schematicLine:schematic) {
            boolean isValueValid = false;
            StringBuilder valueInProgress = new StringBuilder();
            for (char value: schematicLine.toCharArray()) {
                if(Character.isDigit(value)){
                    if(isIndexValid(index, schematic)) isValueValid = true;
                    valueInProgress.append(value);
                } else {
                    if(valueInProgress.length() > 0 && isValueValid){
                        values.add(Integer.valueOf(valueInProgress.toString()));
                        valueInProgress.setLength(0);
                        isValueValid = false;
                    } else {
                        valueInProgress.setLength(0);
                    }
                }
                index++;
            }
            if(valueInProgress.length() > 0 && isValueValid){
                values.add(Integer.valueOf(valueInProgress.toString()));
            }
        }
        return values;
    }

    private boolean isIndexValid (int index, List<String> schematic){
        int schematicLength = schematic.get(0).length();
        int row = index % schematicLength;
        int column = index / schematicLength;

        //row -1         . . . . . . .
        //row            . . 1 1 4 . .
        //row + 1        . . . . . . .

        //row -1         . . . . . . .
        //row            . . 1 1 i * .
        //row + 1        . . . . . . .

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int columnIndex = Math.min(Math.max(column + i, 0), schematic.size() - 1);
                int rowIndex = Math.min(Math.max(row + j, 0),schematicLength - 1);
                if(isSpecial(schematic.get(columnIndex).charAt(rowIndex))){
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean isSpecial(char value){
        return !Character.isDigit(value) && value != '.';
    }

    public void findResult(){
        List<String> input = InputReader.readInputByLine("adventofcode/day3/input.txt", this.getClass());

        System.out.println(findValue(input).stream().reduce(0, Integer::sum));

        System.out.println("Result of day 3 part 1: " + "result");
    }

    public static void main(String[] args) {
        Day3Part1 day3Part1 = new Day3Part1();

        day3Part1.findResult();
    }

}
