package com.example.proiectul.Cinema.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class RepoMemory<T> implements  Interface_repo<T, String> {
    protected final ArrayList<T> list = new ArrayList<>();
    private final Function<T, String> getIdFunction;

    protected RepoMemory(Function<T, String> getIdFunction) {
        this.getIdFunction = getIdFunction;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(list);
    }

    @Override
    public Optional<T> findById(String id) {
        return list.stream().filter(entity -> Objects.equals(getIdFunction.apply(entity), id)).findFirst();
    }

    @Override
    public T save(T entity) {
        Optional<T> existingEntity = findById(getIdFunction.apply(entity));
        existingEntity.ifPresent(list::remove);
        list.add(entity);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        findById(id).ifPresent(list::remove);
    }
}
