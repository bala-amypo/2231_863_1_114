public interface TokenLogRepository {
    List<TokenLog> findByToken_IdOrderByLoggedAtAsc(Long id);
    TokenLog save(TokenLog log);
}
