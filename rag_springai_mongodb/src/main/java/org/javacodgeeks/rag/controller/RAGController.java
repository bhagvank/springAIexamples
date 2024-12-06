package org.javacodegeeks.rag.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.document.Document;

import org.javacodegeeks.rag.model.RAGRequest;
import org.javacodegeeks.rag.service.RAGService;

@RestController
@RequestMapping("/rag")
public class RAGController {

    @Autowired
    private RAGService ragService;
	
	private final ChatModel chatModel;
	
    private final String PROMPT_BLUEPRINT = """
        Answer the query strictly referring the provided context:
        {context}
        Query:
        {query}
        In case you don't have any answer from the context provided, just say:
        I'm sorry I don't have the information you are looking for.
    """;
	
	@Autowired
    public RAGController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/new_request")
    public String addDocuments(@RequestBody List<RAGRequest> documents) {
        ragService.addDocuments(documents);
        return "Documents added successfully";
    }

    @GetMapping("/find")
    public String searchDocuments(
            @RequestParam String query,
            @RequestParam int top_k,
            @RequestParam double threshold) {
        return chatModel.call(createPrompt(query,ragService.searchDocuments(query, top_k, threshold)));
    }

    @GetMapping("/healthcheck")
    public String status() {
        return ragService.getStatus();
    }
	
    private String createPrompt(String query, List<Document> context) {
        PromptTemplate promptTemplate = new PromptTemplate(PROMPT_BLUEPRINT);
        promptTemplate.add("query", query);
        promptTemplate.add("context", context);
        return promptTemplate.render();
    }
}
