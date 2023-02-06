package pl.batonikleonardo.invoicemicroservice.infrastructure.storage;

import org.springframework.data.mongodb.repository.MongoRepository;

interface InvoiceNumberAssignedToOrderDocumentRepository extends MongoRepository<InvoiceNumberAssignedToOrderDocument, String> {
}
