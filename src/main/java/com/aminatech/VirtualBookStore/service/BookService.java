package com.aminatech.VirtualBookStore.service;

import com.aminatech.VirtualBookStore.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Value("${airtable.api.token}")
    private String apiToken;

    @Value("${airtable.base.id}")
    private String baseId;

    @Value("${airtable.table.name}")
    private String tableName;

    private final String airtableUrl = "https://api.airtable.com/v0/";

    public String getBooksJson() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = airtableUrl + baseId + "/" + tableName;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );
            return response.getBody();
        } catch (Exception e) {
            logger.error("Failed to fetch raw books JSON from Airtable", e);
            return null;
        }
    }

    public List<Book> getBooks() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = airtableUrl + baseId + "/" + tableName;

        List<Book> books = new ArrayList<>();

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode records = root.path("records");

            for (JsonNode record : records) {
                JsonNode fields = record.path("fields");

                Book book = new Book();
                book.setId(record.path("id").asText());
                book.setTitle(fields.path("Title").asText(""));
                book.setAuthor(fields.path("Author").asText(""));
                book.setGenre(fields.path("Genre").asText(""));
                book.setIsbn(fields.path("ISBN").asText(""));
                book.setPrice(fields.path("Price").isNumber()
                    ? fields.path("Price").asDouble()
                    : null);

                books.add(book);
            }
        } catch (Exception e) {
            logger.error("Failed to parse books from Airtable JSON", e);
        }

        return books;
    }

    public List<Book> searchBooks(String query) {
        return getBooks().stream()
            .filter(book ->
                (book.getTitle() != null && book.getTitle().toLowerCase().contains(query.toLowerCase())) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(query.toLowerCase())) ||
                (book.getGenre() != null && book.getGenre().toLowerCase().contains(query.toLowerCase()))
            )
            .collect(Collectors.toList());
    }

    public Book getBookById(String bookId) {
        String url = airtableUrl + baseId + "/" + tableName + "/" + bookId;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode record = mapper.readTree(response.getBody());
            JsonNode fields = record.path("fields");

            Book book = new Book();
            book.setId(record.path("id").asText());
            book.setTitle(fields.path("Title").asText(""));
            book.setAuthor(fields.path("Author").asText(""));
            book.setGenre(fields.path("Genre").asText(""));
            book.setIsbn(fields.path("ISBN").asText(""));
            book.setPrice(fields.path("Price").isNumber()
                ? fields.path("Price").asDouble()
                : null);

            return book;

        } catch (Exception e) {
            logger.error("Failed to fetch book with ID: {}", bookId, e);
            return null;
        }
    }
}
