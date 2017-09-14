package parser;

/**
 * Digit é uma classe derivada de AST e que armazena valores (inteiros) de
 * digitos. Trata-se de uma árvore tipo "folha" (não possui filhos esquerdo ou
 * direito)
 * 
 * @author Marco AFS
 *
 */
public class Digit extends AST {
    // Número inteiro do operando
    int value;

    /**
     * Cria um novo operando a partir de um número inteiro.
     * 
     * @param value
     *            O número inteiro do digito.
     */
    public Digit(int value) {
        // Inicializa a parte básica do AST com null
        super(null, null);
        this.value = value;
    }

    /**
     * Retorna o valor do digito
     * 
     * @return O valor do digito
     */
    public int getDigitValue() {
        return this.value;
    }
}
