package parser;

import java.io.IOException;

import token.Token;
import token.TokenClass;

/**
 * A classe Parser é a responsável pela análise sintática
 * 
 * @author Marco AFS
 *
 */
public class Parser {
    // Objeto para o analisador léxico -> retorno de tokens
    private Scanner scanner;
    // Marca atual
    private Token lookAheadToken;

    /**
     * Constrói um analisador sintático que utiizará um scanner para obter
     * tokens.
     * 
     * @param scanner
     *            o scanner que retornará tokens
     */
    public Parser(Scanner scanner) {
        // Instancia o scanner
        this.scanner = scanner;
        // Instancia marca atual
        this.lookAheadToken = null;
    }

    /**
     * Executa a análise sintática e cria uma árvore AST como resultado.
     * 
     * @throws ParserException uma exceção do analisador
     * @throws IOException uma exceção de entrada (i/o)
     * @return a AST resultante do processo de análise
     */
    public AST parse() throws IOException, ParserException {
        // Cria uma AST a partir da expressão
        AST ast = parseExpression();
        // Se depois da análise o último token for EOF, então a análise
        // foi bem sucedida
        if (lookAheadToken.getTokenClass() == TokenClass.EOF) {
            return ast; 
        } else {
            // Senão, a árvore está incompleta - houve erro na análise
            throw new ParserException(String.format("Expected 'EOF': found %c.", 
                    lookAheadToken.getTokenChar()));
        }
    }

    /**
     * Analisa uma expressao. Executa a seguinte construção EBNF:
     *      expression ::= term (('+'|'-') term)*
     * @return a árvore AST resultante
     * @throws IOException
     * @throws ParserException
     */
    private AST parseExpression() throws IOException, ParserException {
        // Veja o EBNF de expression
        // Sempre analisa um termo primeiro
        AST ast = parseTerm();
        AST otherAST = null;
        // Depois pode-se somar ou subtrair zero ou mais termos
        do {
            otherAST = parseSumOperatorTerm(ast);
            if (otherAST != null) {
                ast = otherAST;
            }
        } while (otherAST != null);
        return ast;
    }

    /**
     * Analisa um termo. Executa a seguinte construção EBNF:
     *      term ::= factor (('*'|'/') factor)*
     * @return a árvore AST resultante
     * @throws IOException
     * @throws ParserException
     */
    private AST parseTerm() throws IOException, ParserException {
        // Veja o EBNF de term
        // Sempre analisa um fator
        AST ast = parseFactor();
        AST otherAST = null;
        // Depois pode-se multiplicar ou dividir zero ou mais fatores
        do {
            otherAST = parseMulOperatorFactor(ast);
            if (otherAST != null) {
                ast = otherAST;
            }
        } while (otherAST != null);
        return ast;
    }

    /**
     * Analisa a parte opcional da construção expression:
     *      (('+'|'-') term)
     * 
     * @param leftAST o AST que foi definido à esquerda do operador
     * @return a árvore AST resultante
     * @throws ParserException
     * @throws IOException
     */
    private AST parseSumOperatorTerm(AST leftAST) throws ParserException, IOException {
        // Obter um token
        lookAheadToken = scanner.getToken();
        if (lookAheadToken.getTokenClass() == TokenClass.SUM_OPERATOR) {
            // Obtem o operador
            char op = lookAheadToken.getTokenChar();
            // Analisa o termo seguinte
            AST rightAST = parseTerm();
            // Cria uma árvore com operador '+' ou '-'
            AST ast = new Operator(op, leftAST, rightAST);
            return ast;
        } else { // não tem mais termo
            scanner.unGetToken(lookAheadToken); // Devolve o token
            return null;
        }
    }

    /**
     * Analisa um fator. Executa a seguinte construção EBNF:
     *      factor ::= digit | '(' expression ')' 
     * @return a árvore AST resultante
     * @throws IOException
     * @throws ParserException
     */
    private AST parseFactor() throws IOException, ParserException {
        // Tenta analisar digito
        AST ast = parseDigit();
        if ( ast != null) {
            return ast;
        } else {
            // Senão, tenta analisar uma expressão entre parênteses 
            lookAheadToken = scanner.getToken();
            if (lookAheadToken.getTokenClass() == TokenClass.LPAREN) { // se '('
                // analisa expressão entre '(' e ')'
                ast = parseExpression();
                // Depois tem de encontrar ')'
                lookAheadToken = scanner.getToken();
                if (lookAheadToken.getTokenClass() == TokenClass.RPAREN) {
                    return ast;
                } else {
                    // Lança uma exceção ParserException
                    throw new ParserException(String.format("Expected ')': found %c.", 
                            lookAheadToken.getTokenChar()));
                }
            } else {
             // Lança uma exceção ParserException
                throw new ParserException(String.format("Expected '(': found %c.", 
                        lookAheadToken.getTokenChar()));
            }
        }
    }

    /**
     * Analisa a parte opcional da construção term:
     *      ('*'|'/') factor
     * 
     * @param leftAST o AST que foi definido à esquerda do operador
     * @return a árvore AST resultante
     * @throws ParserException
     * @throws IOException
     */
    private AST parseMulOperatorFactor(AST leftAST) throws ParserException, IOException {
        // Obter um token
        lookAheadToken = scanner.getToken();
        if (lookAheadToken.getTokenClass() == TokenClass.MULT_OPERATOR) {
            // Armazena o operador
            char op = lookAheadToken.getTokenChar();
            // Analisa o fator seguinte
            AST rightAST = parseFactor();
            // Cria uma árvore com operador '*' ou '/'
            AST ast = new Operator(op, leftAST, rightAST);
            return ast;
        } else {
            scanner.unGetToken(lookAheadToken); // Devolve o token
            return null; // Não tem mais fator
        }
    }

    /**
     * Analisa um dígito
     * 
     * @return uma AST contendo o dígito como elemento raiz e árvores esquerda e
     *         direita nulas
     * @throws IOException
     * @throws ParserException
     */
    private AST parseDigit() throws IOException, ParserException {
        // Obter um token
        lookAheadToken = scanner.getToken();
        // Verifica se é um digito
        if (lookAheadToken.getTokenClass() == TokenClass.DIGIT) {
            // Cria e retorna a AST
            AST ast = new Digit(lookAheadToken.getTokenChar() - '0');
            return ast;
        } else {
            scanner.unGetToken(lookAheadToken); // Devolve o token
            return null;
        }

    }

}
