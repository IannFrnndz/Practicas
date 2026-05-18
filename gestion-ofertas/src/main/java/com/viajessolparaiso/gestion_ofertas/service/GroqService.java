package com.viajessolparaiso.gestion_ofertas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viajessolparaiso.gestion_ofertas.dto.OfertaPdfData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public OfertaPdfData analizarTexto(String textoPdf) throws Exception {

        System.out.println("=== INICIANDO ANALISIS IA ===");

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String prompt = """
                Analiza este PDF de una oferta de viajes.

                Extrae:
                - titulo
                - descripcion
                - precio
                - fechaValidez
                - categoria
                - imagenUrl

                Las categorias válidas son:
                DISNEY,
                FESTIVOS,
                ESPAÑA,
                EUROPA,
                AMERICA,
                CARIBE,
                AFRICA,
                ASIA,
                OCEANIA,
                EXCURSIONES,
                ULTIMA_HORA,
                COMUNIDAD_DE_MADRID,
                CRUCEROS

                IMPORTANTE:
                - Devuelve SOLO JSON
                - No expliques nada
                - precio debe ser número
                - fechaValidez formato yyyy-MM-dd
                - categoria debe coincidir EXACTAMENTE

                Texto PDF:
                """ + textoPdf;

        String body = """
                {
                  "model": "llama-3.3-70b-versatile",
                  "messages": [
                    {
                      "role": "user",
                      "content": %s
                    }
                  ],
                  "temperature": 0.2
                }
                """.formatted(objectMapper.writeValueAsString(prompt));


        System.out.println("=== ENVIANDO PETICION A GROQ ===");
        System.out.println(body);

        String response = webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("=== RESPUESTA RECIBIDA ===");
        System.out.println(response);

        JsonNode root = objectMapper.readTree(response);

        String content = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        content = content
                .replace("```json", "")
                .replace("```", "")
                .trim();

        System.out.println("=== JSON LIMPIO ===");
        System.out.println(content);

        return objectMapper.readValue(content, OfertaPdfData.class);
    }
}