package org.javacodegeeks.rag.repository;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;

public interface RAGRepository {

    void addDocuments(List<Document> documents);

    List<Document> searchDocuments(SearchRequest searchRequest);
}
