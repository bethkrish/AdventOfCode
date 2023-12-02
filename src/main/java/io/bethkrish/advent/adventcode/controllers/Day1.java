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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class Day1 {


    Resource inputFile = new ClassPathResource("input-1.txt");

    /**
     * Calculates the sum of calibration values from an input file.
     *
     * @return A string containing the sum of calibration values.
     * @throws IOException If there is an error reading the input file.
     */
    @GetMapping("/calibrationValues")
    public String calculatorSnow() throws IOException {
        // Get input stream from the input file.
        InputStream inputStream = inputFile.getInputStream();
        // Create a buffered reader to read the input stream.
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        int sumOfCalibrationValues = 0;
        int calibrationValue = 0;

        while ((line = reader.readLine()) != null) {

            // Replace any non-numeric characters in the line with empty strings.
            line = replaceStringWithNumbers(line);

            // Create a pattern to match any non-numeric characters in the line.
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(line);

            // Replace any non-numeric characters in the line with empty strings.
            String result = matcher.replaceAll("");

            // Get the first and last characters of the resulting string.
            String firstValue = String.valueOf(result.charAt(0));
            String lastValue = String.valueOf(result.charAt(result.length() - 1));

            // Parse the first and last characters as integers and add them together.
            calibrationValue = Integer.parseInt(firstValue + lastValue);

            // Add the calibration value to the sum of calibration values.
            sumOfCalibrationValues = sumOfCalibrationValues + calibrationValue;
        }

        // Return a string containing the sum of calibration values.
        return "Sum of Calibration Value is " + sumOfCalibrationValues;
    }


    private String replaceStringWithNumbers(String line) {
        String modifiedString = line.replace("twone", "21").replace("oneight", "18").replace("eightwo", "82").replace("threeight", "38").replace("eighthree", "83").replace("fiveight", "58").replace("sevenine", "79").replace("nineight", "98").replace("one", "1").replace("two", "2").replace("three", "3").replace("four", "4").replace("five", "5").replace("six", "6").replace("seven", "7").replace("eight", "8").replace("nine", "9").replace("zero", "0");
        return modifiedString;
    }
}