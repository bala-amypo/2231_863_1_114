// package com.example.demo.service.impl;

// import com.example.demo.entity.QueuePosition;
// import com.example.demo.entity.ServiceCounter;
// import com.example.demo.entity.Token;
// import com.example.demo.entity.TokenLog;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.repository.QueuePositionRepository;
// import com.example.demo.repository.ServiceCounterRepository;
// import com.example.demo.repository.TokenLogRepository;
// import com.example.demo.repository.TokenRepository;
// import com.example.demo.service.TokenService;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.UUID;

// @Service
// public class TokenServiceImpl implements TokenService {
//     private final TokenRepository tokenRepository;
//     private final ServiceCounterRepository serviceCounterRepository;
//     private final TokenLogRepository tokenLogRepository;
//     private final QueuePositionRepository queuePositionRepository;
    
//     public TokenServiceImpl(TokenRepository tokenRepository, ServiceCounterRepository serviceCounterRepository, 
//                            TokenLogRepository tokenLogRepository, QueuePositionRepository queuePositionRepository) {
//         this.tokenRepository = tokenRepository;
//         this.serviceCounterRepository = serviceCounterRepository;
//         this.tokenLogRepository = tokenLogRepository;
//         this.queuePositionRepository = queuePositionRepository;
//     }
    
//     @Override
//     public Token issueToken(Long counterId) {
//         ServiceCounter counter = serviceCounterRepository.findById(counterId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));
        
//         if (!counter.getIsActive()) {
//             throw new IllegalArgumentException("Counter is not active");
//         }
        
//         // Get current waiting tokens to determine position
//         List<Token> waitingTokens = tokenRepository.findByServiceCounter_IdAndStatusOrderByIssuedAtAsc(counterId, "WAITING");
        
//         Token token = new Token();
//         token.setTokenNumber(UUID.randomUUID().toString().substring(0, 8));
//         token.setServiceCounter(counter);
//         token.setStatus("WAITING");
//         token.setIssuedAt(LocalDateTime.now());
        
//         token = tokenRepository.save(token);
        
//         // Create queue position
//         QueuePosition queuePosition = new QueuePosition(token, waitingTokens.size() + 1, LocalDateTime.now());
//         queuePositionRepository.save(queuePosition);
        
//         // Create log entry
//         TokenLog log = new TokenLog(token, "Token issued", LocalDateTime.now());
//         tokenLogRepository.save(log);
        
//         return token;
//     }
    
//     @Override
//     public Token updateStatus(Long tokenId, String status) {
//         Token token = getToken(tokenId);
        
//         if (!isValidStatusTransition(token.getStatus(), status)) {
//             throw new IllegalArgumentException("Invalid status transition from " + token.getStatus() + " to " + status);
//         }
        
//         token.setStatus(status);
//         if ("COMPLETED".equals(status) || "CANCELLED".equals(status)) {
//             token.setCompletedAt(LocalDateTime.now());
//         }
        
//         token = tokenRepository.save(token);
        
//         // Create log entry
//         TokenLog log = new TokenLog(token, "Status updated to " + status, LocalDateTime.now());
//         tokenLogRepository.save(log);
        
//         return token;
//     }
    
//     @Override
//     public Token getToken(Long tokenId) {
//         return tokenRepository.findById(tokenId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
//     }
    
//     private boolean isValidStatusTransition(String currentStatus, String newStatus) {
//         if ("WAITING".equals(currentStatus)) {
//             return "SERVING".equals(newStatus) || "CANCELLED".equals(newStatus);
//         }
//         if ("SERVING".equals(currentStatus)) {
//             return "COMPLETED".equals(newStatus) || "CANCELLED".equals(newStatus);
//         }
//         return false;
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.entity.QueuePosition;
import com.example.demo.entity.ServiceCounter;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenLog;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.QueuePositionRepository;
import com.example.demo.repository.ServiceCounterRepository;
import com.example.demo.repository.TokenLogRepository;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final ServiceCounterRepository serviceCounterRepository;
    private final TokenLogRepository tokenLogRepository;
    private final QueuePositionRepository queuePositionRepository;

    public TokenServiceImpl(TokenRepository tokenRepository,
                            ServiceCounterRepository serviceCounterRepository,
                            TokenLogRepository tokenLogRepository,
                            QueuePositionRepository queuePositionRepository) {
        this.tokenRepository = tokenRepository;
        this.serviceCounterRepository = serviceCounterRepository;
        this.tokenLogRepository = tokenLogRepository;
        this.queuePositionRepository = queuePositionRepository;
    }

    @Override
    public Token issueToken(Long counterId) {

        ServiceCounter counter = serviceCounterRepository.findById(counterId)
                .orElseThrow(() -> new ResourceNotFoundException("Counter not found"));

        Token token = new Token();
        token.setServiceCounter(counter);
        token.setStatus("WAITING");
        token.setIssuedAt(LocalDateTime.now());

        // Generate sequential token number
        long count = tokenRepository.count() + 1;
        token.setTokenNumber("T" + count);

        token = tokenRepository.save(token);

        // Get last queue position safely
        int lastPosition = queuePositionRepository.findMaxPosition();

        QueuePosition queuePosition = new QueuePosition();
        queuePosition.setToken(token);
        queuePosition.setPosition(lastPosition + 1);
        queuePosition.setUpdatedAt(LocalDateTime.now());

        queuePositionRepository.save(queuePosition);

        TokenLog log = new TokenLog(token, "Token issued", LocalDateTime.now());
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public Token updateStatus(Long tokenId, String status) {

        Token token = tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));

        token.setStatus(status);

        if ("COMPLETED".equalsIgnoreCase(status)
                || "CANCELLED".equalsIgnoreCase(status)) {
            token.setCompletedAt(LocalDateTime.now());
        }

        token = tokenRepository.save(token);

        TokenLog log = new TokenLog(token, "Status updated to " + status, LocalDateTime.now());
        tokenLogRepository.save(log);

        return token;
    }

    @Override
    public Token getToken(Long tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }
}

