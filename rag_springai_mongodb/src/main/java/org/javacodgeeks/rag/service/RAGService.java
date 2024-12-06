package org.javacodegeeks.rag.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.javacodegeeks.rag.model.RAGRequest;
import org.javacodegeeks.rag.repository.RAGRepository;

@Service
public class RAGService {

    @Autowired
    private RAGRepository ragRepository;

    public void addDocuments(List<RAGRequest> documents) {
        List<Document> docs = documents.stream()
            .map(doc -> new Document(doc.getContent(), doc.getMetadata()))
            .collect(Collectors.toList());
        ragRepository.addDocuments(docs);
    }

    public List<Document> searchDocuments(String query, int topK, double similarityThreshold) {
        SearchRequest searchRequest = SearchRequest.query(query)
            .withTopK(topK)
            .withSimilarityThreshold(similarityThreshold);

        List<Document> results = ragRepository.searchDocuments(searchRequest);

		   return results;
    }

    public String getStatus() {
        return "RAG app is working fine";
    }
}
