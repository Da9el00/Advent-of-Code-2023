package adventofcode.day12;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class Day12Part2 {

    private final Map<String, Long> cache = new HashMap<>();

    protected long checkDamagedSpringData(List<Character> record, List<Integer> damagedSprings){
        long result = 0;
        String cacheKey = record.toString() + damagedSprings.toString();

        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        if (damagedSprings.size() == 0) {
            long computedResult = record.contains('#') ? 0 : 1;

            cache.put(cacheKey, computedResult);

            return computedResult;
        }

        int current = damagedSprings.get(0);
        List<Integer> remainingSprings = damagedSprings.subList(1, damagedSprings.size());

        for (int i = 0; i < record.size() - sum(remainingSprings) - remainingSprings.size() - current + 1; i++) {
            if (record.subList(0, i).contains('#')) {
                break;
            }

            int nxt = i + current;
            if (nxt <= record.size() && record.subList(i, nxt).stream().noneMatch(ch -> ch == '.') &&
                    (nxt == record.size() || record.get(nxt) != '#')) {
                List<Character> nextRecord =  nxt + 1 < record.size() ? record.subList(nxt + 1, record.size()) :List.of();
                result += checkDamagedSpringData(nextRecord, remainingSprings);
            }
        }
        // Cache the result before returning
        cache.put(cacheKey, result);
        return result;
    }

    public static int sum(List<Integer> values) {
        return values.stream().reduce(Integer::sum).orElse(0);
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

        Optional<Long> result = records.stream().map(record -> {
            record.unfoldSpringData(5);
            return checkDamagedSpringData(record.getRecordData(), record.getRecordRules());
        }).reduce(Long::sum);

        System.out.println("Result of day 12 part 1: " +  result.get());
    }

    public static void main(String[] args) {
        Day12Part2 day2Part21 = new Day12Part2();
        Timer.timeChecker(day2Part21::findResult);
    }
}
