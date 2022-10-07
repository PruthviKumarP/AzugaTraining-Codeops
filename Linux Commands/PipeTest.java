package com.test.java;

import static org.junit.jupiter.api.Assertions.*;

import com.main.java.Commands;
import com.main.java.Pipe;

import org.junit.jupiter.api.Test;

public class PipeTest {

    Commands p = new Pipe();

    @Test
    void testCatSortHead(){
        String expectedOutput = "Apple\nApricot\nAtemoya\nAvocados\n";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | sort | head -4");
        assertEquals(expectedOutput,actualOutput);
    }


    @Test
    void testCatUniqTail(){
        String expectedOutput = "Lychee\nLongan\nLangsat\nMango\nMulberry\n";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | uniq | tail -5");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testCatWc(){
        String expectedOutput = "56\t60\t512";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | wc");

        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testLsWc(){
        String expectedOutput = "3\t6\t38";
        String actualOutput = p.pipes("ls /Users/azuga/Documents/LinuxCommands/src | wc");

        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void testCatHead(){
        String expectedOutput = "Apple\nBanana\nApricot\nAtemoya\nAvocados\nBlueberry\nBlackcurrant\nAckee\n";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | head -8");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testCatTail() {
        String expectedOutput = "Lychee\n" +
                "Longan\n" +
                "Langsat\n" +
                "Mango\n" +
                "Mulberry\n" +
                "Pear\n";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | tail -6");
        assertEquals(expectedOutput, actualOutput);
    }


    @Test
    void testCatSortHeadWc() {
        String expectedOutput = "3\t4\t31";
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/src/fruits.txt | sort | head -4 | wc");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testNegNull() {
        String expectedOutput = null;
        String actualOutput = p.pipes("cat /Users/azuga/Documents/LinuxCommands/fruits.txt | sort | head -4 | wc");
        assertEquals(expectedOutput, actualOutput);
    }
}
