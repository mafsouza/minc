package vm;

import java.io.FileOutputStream;
import java.io.PrintStream;

import parser.AST;
import parser.Digit;
import parser.Operator;

/**
 * A classe Compiler é utilizada para gerar código (aqui, uma máquina virtual
 * fictícia baseada em pilha).
 * 
 * @author Marco AFS
 *
 */
public class Compiler {
    /**
     * output é a stream de saída (arquivo com código de máquina)
     */
    private static PrintStream output = null;

    /**
     * Função que compila (gera o código de máquina) das expressões
     * 
     * @param ast
     *            é a árvore de sintaxe abstrata produzida pelo parser
     */
    public static void compile(AST ast) {
        try {
            if (output == null) {
                // Abre o arquivo uma e única vez
                output = new PrintStream(new FileOutputStream("output.asm"));
            }
            // Se a árvore for um operador
            if (ast instanceof Operator) {
                // Processa os operandos esquerdo e direito
                compile(ast.getLeftAST());
                compile(ast.getRightAST());
                // Converte para Operator
                Operator operator = (Operator) ast;
                // Descobre e executa a operação
                switch (operator.getOperatorSymbol()) {
                case '+':
                    // Gera comando para soma de 2 elementos do topo da pilha
                    output.println("ADD");
                    break;
                case '-':
                    // Gera comando para subtração de 2 elementos do topo da
                    // pilha
                    output.println("SUB");
                    break;
                case '*':
                    // Gera comando para multiplicação de 2 elementos do topo da
                    // pilha
                    output.println("MULT");
                    break;
                case '/':
                    // Gera comando para divisão de 2 elementos do topo da pilha
                    output.println("DIV");
                    break;
                default: // Erro no operador?
                    // Fechar o arquivo
                    if (output != null) {
                        output.close();
                    }
                    throw new Exception("Invalid operator found!");
                }
            } else { // senão, deve ser uma árvore de operando
                // Converte para Digit
                Digit dig = (Digit) ast;
                // Gera comando para PUSH do dígito
                output.printf("PUSH %d\n", dig.getDigitValue());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // Fechar o arquivo
            if (output != null) {
                output.close();
            }
        }
    }
}
