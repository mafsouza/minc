package token;

/**
 * Token é a classe responsável por classificar caracteres da entrada
 * em tokens (marcas) a serem utilizadas pelo Parser
 * @author Marco AFS
 *
 */
public class Token {
    /**
     * Representa um token a ser extraído da entrada
     */
    // Classe do token
    private TokenClass tokenClass;
    // Lexema do token
    private Character tokenChar;

    /**
     * Cria um token a partir de uma classe e caractere
     * 
     * @param tokenClass a classe do token
     * @param tokenChar o símbolo do token
     */
    public Token(TokenClass tokenClass, Character tokenChar) {
        this.tokenClass = tokenClass;
        this.tokenChar = tokenChar;
    }

    /**
     * Retorna o próximo token da entrada
     * 
     * @return próximo token da entrada
     */
    public TokenClass getTokenClass() {
        return this.tokenClass;
    }

    /**
     * Retorna o caractere associado a unm token
     * 
     * @return caractere do token
     */
    public Character getTokenChar() {
        return this.tokenChar;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Token: tokenClass(" + this.tokenClass + "); tokenChar(" + 
                this.tokenChar + ")";

    }

}
