package org.javacodegeeks.rag.repository;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RAGRepositoryImpl implements RAGRepository {

    @Autowired
    private VectorStore vectorStore;

    @Override
    public void addDocuments(List<Document> documents) {
        vectorStore.add(documents);
    }

    @Override
    public List<Document> searchDocuments(SearchRequest searchRequest) {
        return vectorStore.similaritySearch(searchRequest);
    }
}
