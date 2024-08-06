package br.com.systemsgs.transaction_service.repository;

import br.com.systemsgs.transaction_service.model.ModelTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransacaoRepository extends MongoRepository<ModelTransaction, UUID> {}
