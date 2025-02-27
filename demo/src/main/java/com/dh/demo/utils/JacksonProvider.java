package com.dh.demo.utils;

import com.dh.demo.utils.LocalDateDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;

public class JacksonProvider {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new dh.backend.music_store.utils.LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());

        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
