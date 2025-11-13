package com.example.proiectul.Cinema.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class InFileRepository<T> implements BaseRepo<T> {

    private final Path filePath;
    private final Class<T> entityClass;
    private final Function<T,String> getId;
    private final BiConsumer<T,String> setId;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String,T> cache = new LinkedHashMap<>();

    public InFileRepository(Path filePath,
                            Class<T> entityClass,
                            Function<T,String> getId,
                            BiConsumer<T,String> setId) {
        this.filePath = filePath;
        this.entityClass = entityClass;
        this.getId = getId;
        this.setId = setId;

        mapper.findAndRegisterModules();
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        load();
    }

    private void load() {
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) Files.writeString(filePath, "[]");
            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, entityClass);
            List<T> list = mapper.readValue(Files.readAllBytes(filePath), listType);
            cache.clear();
            for (T e: list) cache.put(getId.apply(e), e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load " + filePath, e);
        }
    }

    private void flush() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), new ArrayList<>(cache.values()));
        } catch (IOException e) {
            throw new RuntimeException("Cannot write " + filePath, e);
        }
    }

    @Override
    public List<T> findAll() { return new ArrayList<>(cache.values()); }

    @Override
    public Optional<T> findById(String id) { return Optional.ofNullable(cache.get(id)); }

    @Override
    public T save(T entity) {
        String id = getId.apply(entity);
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
            setId.accept(entity, id);
        }
        cache.put(id, entity);
        flush();
        return entity;
    }

    @Override
    public void deleteById(String id) {
        cache.remove(id);
        flush();
    }
}
