package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        int n = 200;
        String pattern = "bbbbbbbbbsbbbbbbbbsbbbsbbbsbbbbsbsbbsbsbbsbbsbsbsbsbssbsbsbsbsbsssbsssbssbssbsssssbssssssbssssssssss";

        int[] resolvedPattern = patternResolver(pattern, n);

        System.out.println(generator(resolvedPattern, 'b', 's'));
    }

    public static List<Integer> toIntPatter(String pattern) {
        char[] patternList = pattern.toCharArray();
        List<Integer> intPattern = new ArrayList<>();
        int counter = 1;

        for(int i = 1; i < patternList.length; i++) {
            if (patternList[i] == patternList[i-1]) {
                counter++;
            } else {
                intPattern.add(counter);
                counter = 1;
            }
        }
        intPattern.add(counter);
        return intPattern;
    }

    public static String generator(int[] pattern, char firstChar, char secondChar) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < pattern.length; i++) {
            for(int j = 0; j < pattern[i]; j++) {
                if(i % 2 == 0) {
                    result.append(firstChar);
                } else {
                    result.append(secondChar);
                }
            }
        }
        return result.toString();
    }

    public static int[] patternResolver(String pattern, int n) {
        var p = toIntPatter(pattern);
        List<Double> percentPattern = p.stream().map(i -> 100.0 * i / pattern.length()).toList();

        List<Double> result = new ArrayList<>();

        percentPattern.forEach(i -> result.add(n / 100.0 * i));

        int[] roundedResult = new int[result.size()];
        double[] diffs = new double[result.size()];

        for (int i = 0; i < result.size(); i++) {
            roundedResult[i] = (int) Math.floor(result.get(i));
            diffs[i] = result.get(i) - roundedResult[i];
        }
        var roundedSum = IntStream.of(roundedResult).sum();
        var adjustmentNeeded = Math.round(n) - roundedSum;

        IntStream.range(0, result.size())
                .boxed()
                .sorted((i, j) -> Double.compare(diffs[j], diffs[i]))
                .limit(Math.abs(adjustmentNeeded))
                .forEach(i -> roundedResult[i] += (adjustmentNeeded > 0) ? 1 : -1);

        return roundedResult;
    }
}