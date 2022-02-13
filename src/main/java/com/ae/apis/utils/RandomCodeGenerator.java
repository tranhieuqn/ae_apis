package com.ae.apis.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Service
public class RandomCodeGenerator {
    public String generateCode(int numberLength, int alphabeticLength) {
        char[] charArr = (new StringBuilder()).append(randomNumeric(numberLength))
                                            .append(randomAlphabetic(alphabeticLength))
                                            .toString()
                                            .toCharArray();

        Character[] characterArr = ArrayUtils.toObject(charArr);

        List<Character> characterList = Arrays.asList(characterArr);

        Collections.shuffle(characterList);

        return characterList.stream().map(String::valueOf).collect(joining()).toUpperCase();
    }
}
