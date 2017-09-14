package parser;

/**
 * AST armazena uma árvore de sintaxe abstrata (abstract syntax tree) que será
 * criada pelo Parser durante a análise sintática.
 * 
 * @author Marco AFS
 *
 */

public class AST {
    // Árvore AST esquerda
    private AST leftAST;
    // Árvore AST direita
    private AST rightAST;

    /**
     * Cria uma árvore AST a partir de subárvores esquerda e direita
     * 
     * @param leftAST
     *            a árvore AST esquerda
     * @param rightAST
     *            a árvore AST direita
     */
    public AST(AST leftAST, AST rightAST) {
        this.leftAST = leftAST;
        this.rightAST = rightAST;
    }

    /**
     * Retorna a árvore AST esquerda
     * 
     * @return a árvore AST esquerda
     */
    public AST getLeftAST() {
        return this.leftAST;
    }

    /**
     * Retorna a árvore AST direita
     * 
     * @return a árvore AST direita
     */
    public AST getRightAST() {
        return this.rightAST;
    }
}
