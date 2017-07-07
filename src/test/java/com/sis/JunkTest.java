package com.sis;


import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JunkTest {

    @Test
    public void filterAndCountTest() {

        String contents = "aThis is a exceptionally big sentence";
        List<String> words = Arrays.asList(contents.split(" "));


        long count = 0;
        for (String w : words) {
            if (w.length() > 3) count++;
        }
        System.out.println(count);

        count = words.stream().filter(w -> w.length() > 3).filter(ww -> ww.contains("e")).count();
        System.out.println(count);

        count = words.parallelStream().filter(w -> w.length() > 3 && w.contains("e")).count();
        System.out.println(count);


        Stream<String> countStream = Stream.of(contents.split(" "));
        count = countStream.filter(w -> w.length() > 3 && w.contains("e")).count();
        System.out.println("Stream.of" + count);


        //concatenate stream elements with a comma
        String result2 = words.stream().map(Object::toString).collect(Collectors.joining(", "));


        //Following generates infinite stream limited to 100
        Stream<Double> randoms = Stream.generate(Math::random).limit(100);

        //longest string first - sorting
        Stream<String> longestFirst =
                words.stream().sorted(Comparator.comparing(String::length).reversed());

        longestFirst.forEach(w -> {System.out.println("printing word: " + w);});


        //get the logest in a stream - could have used max if I were not using reversed
        Optional<String> longest = words.stream().min(Comparator.comparing(String::length).reversed());
        System.out.println("longest: " + longest.orElse(""));

        //get the maximum in a stream
        Optional<String> last = words.stream().max(String::compareToIgnoreCase);
        System.out.println("last: " + last.orElse(""));


        //If either the inverse method or the squareRoot returns Optional.empty(), the result is empty.

        Optional<Double> result = Optional.of(-4.0).flatMap(JunkTest::inverse).flatMap(JunkTest::squareRoot);


    }


    //sorting example

    //List<Team> teams = getAllTeams();

    //Comparator<Team> stadiumCapacityComparator = (o1, o2)->o1.getStadiumCapacity().compareTo(o2.getStadiumCapacity());
	//teams.sort(stadiumCapacityComparator);




    @Test
    public void statistics()    {

        String contents = "aThis is a exceptionally big sentence";
        List<String> words = Arrays.asList(contents.split(" "));

        IntSummaryStatistics summary = words.stream().collect(
                Collectors.summarizingInt(String::length));


        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();

        System.out.println(averageWordLength);

        System.out.println(maxWordLength);


        //converting to map
       // Map<Integer, Person> idToPerson = people.collect(
       //         Collectors.toMap(Person::getId, Function.identity()));


    }

    @Test
    public void testGroupBy(){
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());

        Map<String, List<Locale>> countryToLocales = locales.collect(
                Collectors.groupingBy(Locale::getCountry));

        System.out.println(countryToLocales.get("CH"));

        Stream<Locale> locales2 = Stream.of(Locale.getAvailableLocales());


        //splitting english and non english locales
        Map<Boolean, List<Locale>> englishAndOtherLocales = locales2.collect(
                Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
        List<Locale> englishLocales = englishAndOtherLocales.get(true);

        System.out.println("english locales" + englishLocales);




    }

    public void predicateTest(){
        //declare a predicate
        Predicate<Integer> p = num -> num % 2 == 0;


        boolean predicateTest = p.test(5);

        System.out.println(predicateTest);

        Predicate<String> tt = isLongMString();
        boolean predicateTest2 = tt.test("here comes Mr Jack");

        System.out.println(predicateTest2);

    }

    public Predicate<String> isLongMString() {
        return p -> p.length() > 6 && p.contains("M");
    }



          public static Optional<Double> inverse(Double x)
     {
                 return x == 0 ? Optional.empty() : Optional.of(1 / x);
              }

              public static Optional<Double> squareRoot(Double x)
     {
                 return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
              }

    @Test
    public void readFileTest() throws IOException {
        Path path = Paths.get("C:\\dev\\ims\\src\\main\\java\\com\\nsm\\ims\\ImsApplication.java");
        try (Stream<String> lines = Files.lines(path))
        {


            final int SIZE = 100;
                     List<String> firstElements = lines
                           .limit(SIZE + 1)
                           .collect(Collectors.toList());

                     for (int i = 0; i < firstElements.size(); i++)
                     {
                         System.out.println(firstElements.get(i));
                     }
        }
    }

}
