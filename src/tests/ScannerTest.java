package tests;

import java.io.ByteArrayInputStream;
import org.junit.Test;

import parser.Scanner;
import token.Token;
import token.TokenClass;

public class ScannerTest {

    void testGetToken(Scanner scanner, Character tokenChar, TokenClass tokenClass) {

        Token token;
        try {
            token = scanner.getToken();
            assert (token.getTokenChar() == tokenChar);
            assert (token.getTokenClass() == tokenClass);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    
    void testUnGetToken(Scanner scanner) {
        Token token1, token2;
        try {
            token1 = scanner.getToken();
            scanner.unGetToken(token1);
            token2 = scanner.getToken();
            assert(token1.getTokenChar().charValue() == 
                    token2.getTokenChar().charValue());
            assert(token1.getTokenClass() == 
                    token2.getTokenClass());
            scanner.unGetToken(token1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Test
    public void testGetScanner() {
        // Simula entrada
        ByteArrayInputStream in = new ByteArrayInputStream(("  */( + \n) - 0  ").getBytes());
        System.setIn(in);
        // Cria um scanner
        Scanner scanner = Scanner.getScanner(System.in);
        testGetToken(scanner, '*', TokenClass.MULT_OPERATOR);
        testGetToken(scanner, '/', TokenClass.MULT_OPERATOR);
        testUnGetToken(scanner);
        testGetToken(scanner, '(', TokenClass.LPAREN);
        testGetToken(scanner, '+', TokenClass.SUM_OPERATOR);
        testGetToken(scanner, ')', TokenClass.RPAREN);
        testUnGetToken(scanner);
        testGetToken(scanner, '-', TokenClass.SUM_OPERATOR);
        testUnGetToken(scanner);
        testGetToken(scanner, '0', TokenClass.DIGIT);
        testGetToken(scanner, '#', TokenClass.EOF);
        System.setIn(System.in);
    }

}
