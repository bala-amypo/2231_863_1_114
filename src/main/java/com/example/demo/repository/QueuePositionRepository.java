public interface QueuePositionRepository {
    Optional<QueuePosition> findByToken_Id(Long id);
    QueuePosition save(QueuePosition qp);
}
