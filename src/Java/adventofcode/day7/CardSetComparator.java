package adventofcode.day7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardSetComparator implements Comparator<String> {

    private static final String RANK_ORDER = "AKQJT98765432";

    @Override
    public int compare(String cardSet1, String cardSet2) {
        List<Comparator<String>> comparators = Arrays.asList(
                this::compareFiveOfAKind,
                this::compareFourOfAKind,
                this::compareFullHouse,
                this::compareThreeOfAKind,
                this::compareTwoPair,
                this::comparePair,
                this::highCardComparator
        );

        for (Comparator<String> comparator : comparators) {
            int result = comparator.compare(cardSet1, cardSet2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

    private int compareFiveOfAKind(String cardSet1, String cardSet2) {
        return Boolean.compare(containsFiveOfAKind(cardSet1), containsFiveOfAKind(cardSet2));
    }

    private int compareFourOfAKind(String cardSet1, String cardSet2) {
        return Boolean.compare(containsFourOfAKind(cardSet1), containsFourOfAKind(cardSet2));
    }

    private int compareFullHouse(String cardSet1, String cardSet2) {
        return Boolean.compare(containsFullHouse(cardSet1), containsFullHouse(cardSet2));
    }

    private int compareThreeOfAKind(String cardSet1, String cardSet2) {
        return Boolean.compare(containsThreeOfAKind(cardSet1), containsThreeOfAKind(cardSet2));
    }

    private int compareTwoPair(String cardSet1, String cardSet2) {
        return Integer.compare(countPairs(cardSet1), countPairs(cardSet2));
    }

    private int comparePair(String cardSet1, String cardSet2) {
        return Boolean.compare(containsPair(cardSet1), containsPair(cardSet2));
    }

    private int highCardComparator(String cardSet1, String cardSet2) {
        Map<Character, Integer> rankMap = createRankMap();

        for (int i = 0; i < cardSet1.length(); i++) {
            char digit1 = cardSet1.charAt(i);
            char digit2 = cardSet2.charAt(i);

            int rank1 = rankMap.get(digit1);
            int rank2 = rankMap.get(digit2);

            int result = Integer.compare(rank1, rank2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private int countPairs(String cardSet) {
        String sortedCardSet = sortCharsInString(cardSet);
        Pattern pattern = Pattern.compile("(.)\\1");
        Matcher matcher = pattern.matcher(sortedCardSet);
        return (int) matcher.results().count();
    }

    private Map<Character, Integer> createRankMap() {
        Map<Character, Integer> rankMap = new HashMap<>();
        for (int i = RANK_ORDER.length() - 1; i >= 0; i--) {
            char rank = RANK_ORDER.charAt(i);
            rankMap.put(rank, RANK_ORDER.length() - i);
        }
        return rankMap;
    }

    private boolean containsPair(String cardSet){ //WORKS
        String regex = "(.).*\\1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardSet);
        return matcher.find();
    }

    private String sortCharsInString(String input){
        char[] charArray = input.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    private boolean containsThreeOfAKind(String cardSet){ //WORKS
        String regex = "(.).*\\1.*\\1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardSet);
        return matcher.find();
    }

    private boolean containsFourOfAKind(String cardSet){//WORKs
        String regex = "(.).*\\1.*\\1.*\\1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardSet);
        return matcher.find();
    }

    private boolean containsFiveOfAKind(String cardSet){
        String regex = "(.)\\1\\1\\1\\1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardSet);
        return matcher.find();
    }

    private boolean containsFullHouse(String cardSet){
        String sortedCardSet = sortCharsInString(cardSet);
        String regex1 = "(.)\\1\\1(.)\\2";
        String regex2 = "(.)\\1(.)\\2\\2";
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher1 = pattern1.matcher(sortedCardSet);
        Matcher matcher2 = pattern2.matcher(sortedCardSet);
        return matcher1.find() || matcher2.find();
    }

    //For testing
    public static void main(String[] args) {
        CardSetComparator cardSetComparator = new CardSetComparator();
        System.out.println(cardSetComparator.containsFullHouse("KKQQQ"));
    }
}
