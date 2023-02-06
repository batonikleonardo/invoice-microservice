package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

interface InvoiceEventRepository extends MongoRepository<InvoiceCreatedEventDocument, String> {
}
