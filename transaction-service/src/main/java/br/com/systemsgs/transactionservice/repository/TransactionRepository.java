package br.com.systemsgs.transactionservice.repository;

import br.com.systemsgs.transactionservice.model.ModelTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends MongoRepository<ModelTransaction, UUID> {}
