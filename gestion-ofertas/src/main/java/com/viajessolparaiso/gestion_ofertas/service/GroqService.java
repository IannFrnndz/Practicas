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

        REGLAS IMPORTANTES:

        - Devuelve SOLO JSON válido
        - No expliques nada
        - precio debe ser número
        - fechaValidez formato yyyy-MM-dd
        - categoria debe coincidir EXACTAMENTE
        - Si aparecen varios países europeos usa EUROPA
        - Si no estás seguro de la categoría usa la más general posible
        - NUNCA dejes categoria vacía
        - NUNCA inventes categorías nuevas
        - categoria debe ser obligatoriamente una de las categorías válidas
        EJEMPLOS DE CATEGORIAS:
        
        - Albania y Macedonia -> EUROPA
        - Punta Cana -> CARIBE
        - Japón -> ASIA
        - Disney Orlando -> DISNEY
        - Crucero Mediterráneo -> CRUCEROS
        
        - NO inventes información
        - La descripcion debe ser comercial y clara
        - La descripcion debe incluir:
            * destino
            * duración
            * hotel si aparece
            * régimen alimenticio
            * vuelos si aparecen
        - La descripcion debe ser un resumen comercial estructurado
        - Resume únicamente la información importante para el cliente
        - Ignora condiciones legales, teléfonos y textos promocionales irrelevantes
        - Si existen listas de servicios o visitas, conviértelas en puntos resumidos
        - La descripcion debe ser clara y fácil de leer
        - NO uses saltos de línea reales
        - Usa \\n para separar líneas en el JSON 
        - Máximo 800 caracteres
        - La última línea de la descripcion debe ser SIEMPRE:
          PRECIO DESDE: X€
        - Sustituye X por el precio extraído
        - PRECIO DESDE debe ir en MAYÚSCULAS

        EJEMPLO DE DESCRIPCION IDEAL:

        
        Descubre Egipto con vuelos y crucero por el Nilo.\\\\n\\\\nEl viaje incluye:\\\\n- Crucero por el Nilo\\\\n- Estancia en El Cairo\\\\n\\\\nPRECIO DESDE: 1299€

       
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

        OfertaPdfData data = objectMapper.readValue(content, OfertaPdfData.class);

        if (data.getDescripcion() != null) {
            data.setDescripcion(
                    data.getDescripcion().replace("\\n", "\n")
            );
        }

        return data;
    }
}