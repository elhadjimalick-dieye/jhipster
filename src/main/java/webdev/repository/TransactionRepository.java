package webdev.repository;
import webdev.domain.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction where transaction.userEnvoyer.login = ?#{principal.username}")
    List<Transaction> findByUserEnvoyerIsCurrentUser();

    @Query("select transaction from Transaction transaction where transaction.userReceveur.login = ?#{principal.username}")
    List<Transaction> findByUserReceveurIsCurrentUser();

}
