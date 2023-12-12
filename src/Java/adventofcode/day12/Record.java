package adventofcode.day12;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Record {
    private List<Character> recordData;
    private LinkedList<Integer> recordRules;

    public Record(List<Character> recordData, LinkedList<Integer> recordRules) {
        this.recordData = recordData;
        this.recordRules = recordRules;
    }


    public List<Character> getRecordData() {
        return recordData;
    }

    public LinkedList<Integer> getRecordRules() {
        return recordRules;
    }

    private List<Integer> duplicateList(List<Integer> originalList, int n) {
        List<Integer> duplicatedList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            duplicatedList.addAll(originalList);
        }

        return duplicatedList;
    }

    private List<Character> duplicateListChar(List<Character> originalList, int n) {
        List<Character> duplicatedList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            duplicatedList.addAll(originalList);
            if(n != 1 && i != (n - 1)){
                duplicatedList.add('?');
            }
        }

        return duplicatedList;
    }

    public void unfoldSpringData(int n){
        recordData = duplicateListChar(recordData, n);
        recordRules = new LinkedList<>(duplicateList(recordRules, n));
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordData=" + recordData +
                ", recordRules=" + recordRules +
                '}';
    }
}
