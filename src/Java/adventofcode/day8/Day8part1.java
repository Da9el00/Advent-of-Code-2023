package adventofcode.day8;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;

public class Day8part1 {

    Map<String, List<String>> nodes = new HashMap<>();
    List<Character> directions;

    private void buildNodesAndDirections(){
        List<String> input = InputReader.readInputByLine("adventofcode/day8/input.txt", this.getClass());

        directions =input.get(0)
                .chars()
                .mapToObj(c -> (char) c)
                .toList();

        for (int i = 2; i < input.size() ; i++) {
            List<String> values = Arrays.stream(input.get(i).split("=")).toList();

            String currentNode = values.get(0).trim();
            List<String> directionPaths = Arrays.stream(values.get(1)
                            .trim()
                            .replace("(", "")
                            .replace(")", "")
                            .split(","))
                    .map(String::trim)
                    .toList();
            nodes.put(currentNode, directionPaths);
        }
    }

    public void findResult(){
        buildNodesAndDirections();

        int pathCounter = 0;
        String currentNode = "AAA";
        LinkedList<Character> path = new LinkedList<>(directions);

        while(!Objects.equals(currentNode, "ZZZ")){
            if(path.isEmpty()){
                path = new LinkedList<>(directions);
            }
            Character nextPathString = path.pop();
            int nextPath = Objects.equals(nextPathString, 'R') ? 1 : 0;

            currentNode = nodes.get(currentNode).get(nextPath);
            pathCounter++;
        }

        System.out.println("Result of day 8 part 1: " + pathCounter + " nodes visited");
    }

    public static void main(String[] args) {
        Day8part1 day8part1 = new Day8part1();
        Timer.timeChecker(day8part1::findResult);
    }
}
