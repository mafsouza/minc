package parser;

/**
 * Operator é uma classe derivada de AST que armazena o símbolo de operação
 * aritmética (+/-) e expressões como operandos
 * 
 * @author Marco AFS
 *
 */

public class Operator extends AST {
    // Símbolo do operador
    private char operator;

    /**
     * Cria uma AST do tipo operador. Um AST tipo operador representa uma árvore
     * cuja raiz é operador e cujos operandos esquerdo e direito são árvores AST
     * (que podem ser do tipo operador ou operando.
     * 
     * @param op
     *            O símbolo do operador ('+', '-', '*', '/')
     * @param leftAST
     *            operador ou operando esquerdo
     * @param rightAST
     *            operador ou operando direito
     */
    public Operator(char op, AST leftAST, AST rightAST) {
        // Inicializa a parte básica do AST
        super(leftAST, rightAST);
        this.operator = op;
    }

    /**
     * Retoorna o símbolo do operador
     * 
     * @return símbolo do operador
     */
    public char getOperatorSymbol() {
        return this.operator;
    }

}
