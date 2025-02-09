package ai.fabanonymous.my_code_agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MyIngestionService {

    private static final Logger log = LoggerFactory.getLogger(MyIngestionService.class);
    private final VectorStore vectorStore;

    @Value("classpath:/docs/rag/test/article_thebeatoct2024.pdf")
    private Resource marketPDF;

    public MyIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingest() throws Exception {
        var pdfReader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        log.info("VectorStore Loaded with data!");
    }
}
