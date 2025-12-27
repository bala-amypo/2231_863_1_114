public interface TokenRepository {
    Optional<Token> findById(Long id);
    Optional<Token> findByTokenNumber(String tokenNumber);
    List<Token> findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(Long id, String status);
    Token save(Token token);
}
