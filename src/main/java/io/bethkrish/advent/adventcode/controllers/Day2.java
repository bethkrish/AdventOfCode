package io.bethkrish.advent.adventcode.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class Day2 {

    Resource inputFile = new ClassPathResource("input-2.txt");

    /**
     * Calculates the sum of calibration values from an input file.
     *
     * @return A string containing the sum of calibration values.
     * @throws IOException If there is an error reading the input file.
     */
    @GetMapping("/possibleGames")
    public String validGames() throws IOException {
        // Get input stream from the input file.
        InputStream inputStream = inputFile.getInputStream();
        // Create a buffered reader to read the input stream.
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int sumOfPossibleGames = 0;
        int gameID = 1;
        HashMap<Integer, List<HashMap<String, Integer>>> gameHashMap = new HashMap<Integer, List<HashMap<String, Integer>>>();

        while ((line = reader.readLine()) != null) {
            String games[] = line.split(":");

            String[] setDetails = games[1].trim().split(";");

            // Iterate through sets in each line
            int setId = 1;
            List<HashMap<String, Integer>> rounds = new ArrayList<>();
            for (String set : setDetails) {

                HashMap<String, Integer> setScores = getSetHashMap(set.trim());
                //Add all rounds to Game
                rounds.add(setScores);
                setId++;
            }
            gameHashMap.put(Integer.valueOf(gameID), rounds);

            gameID++;
        }

        String returnValue = "";
        for (Integer id : gameHashMap.keySet()) {
            returnValue = returnValue + id + ": " + gameHashMap.get(id);
        }

        //By this time we have got the HashMap built. Now it is time to do the validation asked in the puzzle
        //Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
        int sumOfGameIds = 0;
        for (Integer id : gameHashMap.keySet()) {
            if (isThisGamePossible(gameHashMap.get(id))) {
                sumOfGameIds = sumOfGameIds + id;
            }
            ;
        }

        //Puzzle Part 2:
        //What is the sum of the power of these sets?
        int sumOfPower = 0;
        for (Integer id : gameHashMap.keySet()) {
            sumOfPower = sumOfPower + calculatePower(gameHashMap.get(id));
        }

        return "Sum of Possible Games ID is " + sumOfGameIds + "And Sum of Power is " + sumOfPower;
    }

    private int calculatePower(List<HashMap<String, Integer>> gameScore) {
        int minRedRequired=0;
        int minGreenRequired=0;
        int minBlueRequired=0;

        for (HashMap<String, Integer> score : gameScore) {
            for (String cubeColour : score.keySet()) {
                Integer count = score.get(cubeColour);
                if (cubeColour.equals("red")) {
                    if (count.intValue() > minRedRequired) {
                        minRedRequired = count.intValue();
                    }
                } else if (cubeColour.equals("green")) {
                    if (count.intValue() > minGreenRequired) {

                        minGreenRequired = count.intValue();
                    }
                } else if (cubeColour.equals("blue")) {
                    if (count.intValue() > minBlueRequired) {
                        minBlueRequired = count.intValue();
                    }
                }
            }
        }
        return minRedRequired * minGreenRequired * minBlueRequired;
    }

    private boolean isThisGamePossible(List<HashMap<String, Integer>> gameScore) {
        boolean isThisGamePossible;
        for (HashMap<String, Integer> score : gameScore) {
            for (String cubeColour : score.keySet()) {
                Integer count = score.get(cubeColour);
                if (cubeColour.equals("red")) {
                    if (count.intValue() > 12) {
                        return false;
                    }
                } else if (cubeColour.equals("green")) {
                    if (count.intValue() > 13) {
                        return false;
                    }
                } else if (cubeColour.equals("blue")) {
                    if (count.intValue() > 14) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Input to this method looks like "5 green, 7 red, 4 blue"
     * Construct a HashMap of the score against each colour and return
     *
     * @param scores
     * @return
     */
    private HashMap<String, Integer> getSetHashMap(String scores) {
        HashMap<String, Integer> scoresHashMap = new HashMap<>();
        String cubeCountsValues[] = scores.split(",");
        for (String value : cubeCountsValues) {
            String[] cubeCounts = value.trim().split(" ");
            scoresHashMap.put(cubeCounts[1], Integer.valueOf(Integer.parseInt(cubeCounts[0])));
        }

        return scoresHashMap;
    }

}