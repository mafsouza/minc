package vm;

import parser.AST;
import parser.Digit;
import parser.Operator;

/**
 * Interpreter é a classe que interpreta uma expressão e produz um resultado
 * inteiro
 * 
 * @author Marco AFS
 *
 */
public class Interpreter {
    /**
     * @param ast
     *            a árvore de sintaxe abstrata produzida pelo parser
     * @return o valor calculado da expressão
     * @throws Exception exceção geral durante a análise
     */
    public static int interpret(AST ast) throws Exception {
        // Se a árvore for um operador
        if (ast instanceof Operator) {
            // Converte para Operator
            Operator operator = (Operator) ast;
            // Descobre e executa a operação
            switch (operator.getOperatorSymbol()) {
            case '+':
                return interpret(ast.getLeftAST()) + interpret(ast.getRightAST());
            case '-':
                return interpret(ast.getLeftAST()) - interpret(ast.getRightAST());
            case '*':
                return interpret(ast.getLeftAST()) * interpret(ast.getRightAST());
            case '/':
                return interpret(ast.getLeftAST()) / interpret(ast.getRightAST());
            default:
                throw new Exception("Invalid operator found!");
            }
        } else { // senão, deve ser uma árvore de operando
            // Converte para Digit
            Digit dig = (Digit) ast;
            // Retorna o valor do dígito obtido
            return dig.getDigitValue();
        }
    }
}
