package ai.fabanonymous.my_code_agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * See Dan Vega on YouTube: Java + RAG: Create an AI-Powered Financial Advisor using Spring AI,
 * at https://www.youtube.com/watch?v=6Pgmr7xMjiY
 * Github repo: https://github.com/danvega/java-rag
 */
@RestController
public class MyRestController {

    private final ChatClient chatClient;
    private final ChatClient chatClientForChatLikePirate;

    public MyRestController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
        this.chatClientForChatLikePirate = builder
                .defaultSystem("You are a helpful assistant that replies to user messages as if you were the Blackbeard Pirate.")
                .build();
    }

    // http :8080/test/rag message=="How did the Federal Reserve's recent interest rate cut impact various asset classes according to the analysis"
    @GetMapping("/test/rag")
    public String testRag(@RequestParam(value = "message", defaultValue = "How did the Federal Reserve's recent interest rate cut impact various asset classes according to the analysis") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    // http :8080/test/chat message=="Say Hello"
    @GetMapping("/test/chat")
    public String testChatLikePirate(@RequestParam(value = "message", defaultValue = "Say Hello") String message) {
        return chatClientForChatLikePirate.prompt()
                .user(message)
                .call()
                .content();
    }
}
