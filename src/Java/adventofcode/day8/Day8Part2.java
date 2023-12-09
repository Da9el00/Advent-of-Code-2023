package adventofcode.day8;

import adventofcode.utill.InputReader;
import adventofcode.utill.Timer;

import java.util.*;

public class Day8Part2 {
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

    private List<String> findStartingNodes(){
        return nodes.keySet().stream().filter(nodeName -> nodeName.charAt(2) == 'A').toList();
    }

    private int findPathCountFotNode(String node){
        int pathCounter = 0;
        String currentNode = node;
        LinkedList<Character> path = new LinkedList<>(directions);

        while(!(currentNode.charAt(2) == 'Z')){
            if(path.isEmpty()){
                path = new LinkedList<>(directions);
            }
            Character nextPathString = path.pop();
            int nextPath = Objects.equals(nextPathString, 'R') ? 1 : 0;

            currentNode = nodes.get(currentNode).get(nextPath);
            pathCounter++;
        }
        return pathCounter;
    }
    private long lcm(long x, long y) {
        long max = Math.max(x, y);
        long min = Math.min(x, y);
        long lcm = max;
        while (lcm % min != 0) {
            lcm += max;
        }
        return lcm;
    }

    public void findResult(){
        buildNodesAndDirections();

        //Starting nodes
        List<String> currentNodes = findStartingNodes();

        //First path to an end destination
        List<Integer> pathsToFirstGoal = currentNodes.stream().map(this::findPathCountFotNode).toList();

        long result = pathsToFirstGoal.get(0);

        //Find first path count where all paths hit the same common goal
        for (int i = 1; i < pathsToFirstGoal.size(); i++) {
            result = lcm(result, pathsToFirstGoal.get(i));
        }

        System.out.println("Result of day 8 part 1: " + result + " nodes visited");
    }

    public static void main(String[] args) {
        Day8Part2 day8part2 = new Day8Part2();
        Timer.timeChecker(day8part2::findResult);
    }
}
