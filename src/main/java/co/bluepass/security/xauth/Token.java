package co.bluepass.security.xauth;

/**
 * The type Token.
 */
public class Token {

    /**
     * The Token.
     */
    String token;
    /**
     * The Expires.
     */
    long expires;

    /**
     * Instantiates a new Token.
     *
     * @param token   the token
     * @param expires the expires
     */
    public Token(String token, long expires){
        this.token = token;
        this.expires = expires;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets expires.
     *
     * @return the expires
     */
    public long getExpires() {
        return expires;
    }
}
