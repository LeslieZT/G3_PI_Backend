package com.dh.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DataConversor implements IDataConversor {
    private final ObjectMapper objectMapper = JacksonProvider.getObjectMapper(); // ✅ Reutiliza la instancia global

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON a objeto: " + e.getMessage(), e);
        }
    }
}
