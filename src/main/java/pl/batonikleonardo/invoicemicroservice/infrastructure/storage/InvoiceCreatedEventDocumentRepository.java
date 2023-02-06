package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

interface InvoiceCreatedEventDocumentRepository extends MongoRepository<InvoiceCreatedEventDocument, String> {
}
