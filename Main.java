package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {

            String fileName = ".......letterfrequencies-master/src/main/resources/FoundationSeries.txt";

            int avg = 0;
            for (int y=0; y<10; y++) {

            long startTime = System.nanoTime();
            Reader reader = new FileReader(fileName);

            Map<Integer, Long> freq = new HashMap<>();
            //tallyChars(reader, freq);
            newsolution(fileName, freq);
            //print_tally(freq);

            long timeElapsed = System.nanoTime() - startTime;
            //System.out.println("Time elapsed (millisec): " + timeElapsed / 1_000_000);
            avg+=timeElapsed/ 1_000_000;

            System.out.println(timeElapsed / 1_000_000);
        }
        System.out.println("average " + avg/10);
    }

    private static void newsolution(String fileName, Map<Integer, Long> freq)
    {
                BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(fileName));

            int b = 0;

            while ((b = in.read()) != -1) {
                try {
                    //freq.put(b, freq.get(b) + 1);
                    freq.computeIfPresent(b, (Integer key, Long value) -> ++value);
                } catch (NullPointerException np) {
                    freq.put(b, 1L);
                }
            }
        } catch (IOException ex1) {
            System.out.println(ex1);

        }
    }

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {

        int b;

        while ((b = reader.read()) != -1) {
            //System.out.println(freq);
            try {
                freq.put(b, freq.get(b) + 1);
                } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
            //System.out.println(b + "," +freq.get(b)+1);
        }

    }



    private static void print_tally(Map<Integer, Long> freq) {

        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }

        //System.out.println(timeElapsed / 1_000_000);
    }
}
