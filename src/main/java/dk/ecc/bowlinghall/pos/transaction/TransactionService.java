package dk.ecc.bowlinghall.pos.transaction;

import dk.ecc.bowlinghall.booking.bowling.BowlingBooking;
import dk.ecc.bowlinghall.booking.bowling.BowlingBookingDTO;
import dk.ecc.bowlinghall.pos.product.SaleProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    private Transaction toEntity(TransactionDTO requestDTO) {
        return new Transaction(
                requestDTO.amount()
        );
    }

    private TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getAmount()
        );
    }

    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
        var transaction = toEntity(transactionDTO);
        var savedBooking = transactionRepository.save(transaction);
        return toDTO(savedBooking);
    }

    public List<TransactionDTO> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(this::toDTO)
                .toList();
    }
}
