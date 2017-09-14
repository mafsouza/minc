package tests;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import parser.AST;
import parser.Parser;
import parser.ParserException;
import parser.Scanner;

public class ParserTest {
    private Parser parser;
    @Before
    public void setUp() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(("5/7*(2-3)-1").getBytes());
        System.setIn(in);
        parser = new Parser(Scanner.getScanner(System.in));
    }

    @Test
    public void testParse() {
        try {
            AST ast = parser.parse();
        } catch (IOException | ParserException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @AfterClass
    public static void restore() {
        System.setIn(System.in);
    }

}
