package parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;

import token.Token;
import token.TokenClass;

/**
 * Classe reponsável por "tokenizar" a entrada, aceitando apenas tokens válidos
 * da gramática. Para ler a entrada caractere à caractere será utilizada a
 * classe java.io.Reader
 * 
 * @author Marco AFS
 *
 */
public class Scanner {
    // Objeto singleton de scanner
    private static Scanner scanner = null;
    // Objeto que efetivamente fará a extração de tokens
    // da classe java.util.Scanner - permite a leitura e
    // devolução de caractere à entrada
    private PushbackReader source;

    /**
     * Construtor privado para implementar uma lógica de "singleton" (uma classe
     * que cria apenas um e único objeto.
     * 
     * @param source
     *            Representa um leitor de dados do scanner - deve ser derivado
     *            da classe InputStream.
     */
    private Scanner(InputStreamReader source) {
        // Instancia o objeto reader
        // PushbackReader permite tanto a leitura quanto a
        // devolução de caractere à entrada
        this.source = new PushbackReader(source);
    }

    /**
     * 
     * Retorna um único objeto de scanner para ser utilizado posteriormente pelo
     * parser.
     * 
     * @param source
     *            Representa a fonte de dados do scanner - pode ser System.in (a
     *            entrada padrão via teclado) ou um objeto de arquivo (neste
     *            caso, cabe ao programa gerenciar sua abertura e fechamento).
     * @return Retorna um objeto (único) para realizar a análise léxica.
     */
    public static Scanner getScanner(InputStream source) {
        // Se o scanner ainda não foi criado
        if (Scanner.scanner == null) {
            // Então crie-o
            Scanner.scanner = new Scanner(new InputStreamReader(source));
        }
        // Retornar o scanner
        return Scanner.scanner;
    }

    /**
     * Retorna true se o caractere c for um dígito (0-9)
     * 
     * @param c
     *            o caractere a ser testado
     * @return true se c for dígito; caso contrário, retorna false
     */
    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    /**
     * Retorna true se o caractere c for um separador (espaço, nova linha, tab,
     * por exemplo)
     * 
     * @param c
     *            o caractere a ser testado
     * @return true se c for espaço; caso contrário, retorna false
     */
    private boolean isSpace(char c) {
        return Character.isWhitespace(c);
    }

    /**
     * Retorna true se o caractere c for um operador aritmético
     * 
     * @param c
     * @return true se c for operador aritmético; caso contrário, retorna false
     */
    private boolean isSumOperator(char c) {
        return (c == '+' || c == '-') ? true : false;
    }

    /**
     * Retorna true se o caractere c for um operador aritmético
     * 
     * @param c
     * @return true se c for operador aritmético; caso contrário, retorna false
     */
    private boolean isMultOperator(char c) {
        return (c == '*' || c == '/') ? true : false;
    }

    /**
     * Retorna true se o caractere c for parêntese à esquerda
     * 
     * @param c
     * @return true se c for for parêntese à esquerda; caso contrário, retorna
     *         false
     */
    private boolean isLParen(char c) {
        return (c == '(') ? true : false;
    }

    /**
     * Retorna true se o caractere c for parêntese à direita
     * 
     * @param c
     * @return true se c for for parêntese à direita; caso contrário, retorna
     *         false
     */
    private boolean isRParen(char c) {
        return (c == ')') ? true : false;
    }

    /**
     * Retorna um token (marca) de acordo com a gramática especificada.
     * 
     * @return uma marca reconhecida da entrada.
     * @throws IOException uma possível exceção de i/o
     */
    public Token getToken() throws IOException {
        // Armazena o código do caractere lido por read()
        int r;
        // Utilizar o objeto reader para ler e ignorar espaços
        // eventualmente terminando se encotrar fim de arquivo
        do {
            r = source.read();
            if (r < 0) {
                return new Token(TokenClass.EOF, '#');
            }
        } while (isSpace((char) r));
        // Verifica os tokens aceitos
        if (isDigit((char) r)) { // Se dígito
            return new Token(TokenClass.DIGIT, (char) r);
        } else if (isSumOperator((char) r)) { // Se operador +,-
            return new Token(TokenClass.SUM_OPERATOR, (char) r);
        } else if (isMultOperator((char) r)) { // Se operador *,/
            return new Token(TokenClass.MULT_OPERATOR, (char) r);
        } else if (isLParen((char) r)) { // Se '('
            return new Token(TokenClass.LPAREN, (char) r);
        } else if (isRParen((char) r)) { // Se ')'
            return new Token(TokenClass.RPAREN, (char) r);
        } else { // É outro token...
            return new Token(TokenClass.OTHER, (char) r);
        }
    }

    /**
     * Devolve um caractere à entrada armazenado em token
     * 
     * @param token
     *            o token que se devolverá à entrada
     * @throws IOException uma possível exceção de i/o
     */
    public void unGetToken(Token token) throws IOException {
        // Devolve o caractere à entrada
        if(token.getTokenClass() != TokenClass.EOF) {
            source.unread(token.getTokenChar());
        }
    }

}
