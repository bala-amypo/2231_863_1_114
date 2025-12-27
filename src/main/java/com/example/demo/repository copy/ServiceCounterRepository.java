public interface ServiceCounterRepository {
    Optional<ServiceCounter> findById(Long id);
    List<ServiceCounter> findByIsActiveTrue();
    ServiceCounter save(ServiceCounter sc);
}
