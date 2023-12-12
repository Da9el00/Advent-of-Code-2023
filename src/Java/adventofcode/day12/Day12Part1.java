package adventofcode.day12;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12Part1 {

    protected boolean isRecordValidSoFar(List<Character> record, LinkedList<Integer> damagedSprings){
        LinkedList<Integer> localDamagedSpringRules = new LinkedList<>(damagedSprings);
        boolean validRecord = true;
        boolean inSpringConnection = false;
        Integer currentCheck;
        if(!localDamagedSpringRules.isEmpty()){
            currentCheck = localDamagedSpringRules.poll();
        } else {
            return true;
        }

        for (Character character : record) {
            if (character == '?') {
                return true;
            }
            if (currentCheck == 0) {
                inSpringConnection = false;
                if (character == '.') {
                    if (!localDamagedSpringRules.isEmpty()) {
                        currentCheck = localDamagedSpringRules.poll();
                    }
                } else {
                    validRecord = false;
                    break;
                }
            } else {
                if (character == '.') {
                    if (inSpringConnection) {
                        validRecord = false;
                        break;
                    }
                    continue;
                }

                if (character == '#') {
                    currentCheck = currentCheck - 1;
                    inSpringConnection = true;
                } else {
                    validRecord = false;
                    break;
                }
            }
        }
        if(!localDamagedSpringRules.isEmpty() || currentCheck != 0){
            validRecord = false;
        }

        return  validRecord;
    }

    protected List<List<Character>> generateCombinations(List<Character> brokenRecordData, LinkedList<Integer> rules) {
        Optional<Integer> indexOfMissingValue = IntStream.range(0, brokenRecordData.size())
                .filter(i -> brokenRecordData.get(i) == '?')
                .boxed()
                .findFirst();
        if(indexOfMissingValue.isEmpty()){
            return new ArrayList<>(List.of(brokenRecordData));
        }
        ArrayList<List<Character>> result = new ArrayList<>();
        if (isRecordValidSoFar(brokenRecordData, rules)){
            List<List<Character>> result1 = generateCombinations(replaceChar(brokenRecordData, '.', indexOfMissingValue.get()), rules);
            List<List<Character>> result2 = generateCombinations(replaceChar(brokenRecordData, '#', indexOfMissingValue.get()), rules);
            result.addAll(result1);
            result.addAll(result2);
        }

        return result;
    }

    protected List<Character> replaceChar(List<Character> brokenRecordData, Character character, int i){
        List<Character> copyRecordSpring = new ArrayList<>(brokenRecordData);
        copyRecordSpring.set(i, character);
        return copyRecordSpring;
    }

    protected List<Record> buildRecordList(){
        return InputReader.readInputByLine("adventofcode/day12/input.txt", this.getClass())
                .stream().map(stringValue -> {
                    String[] parts = stringValue.split(" ");
                    List<Character> recordData = parts[0].chars().mapToObj(c -> (char) c).toList();

                    LinkedList<Integer> recordRules = Arrays.stream(parts[1].split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toCollection(LinkedList::new));

                    return new Record(recordData, recordRules);
                }).toList();
    }

    public void findResult(){
        List<Record> records = buildRecordList();

        Optional<Integer> result = records.stream().map(record -> {
            List<List<Character>> possibleValues = generateCombinations(record.getRecordData(), record.getRecordRules());

            return possibleValues.stream().filter(possibleRecord -> isRecordValidSoFar(possibleRecord, record.getRecordRules()))
                    .toList().size();
        }).reduce(Integer::sum);

        System.out.println("Result of day 12 part 1: " +  result.get());
    }

    public static void main(String[] args) {
        Day12Part1 day12Part1 = new Day12Part1();
        Timer.timeChecker(day12Part1::findResult);
    }
}
