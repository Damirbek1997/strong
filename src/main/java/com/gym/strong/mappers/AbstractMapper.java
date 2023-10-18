package com.gym.strong.mappers;

import java.util.List;
import java.util.stream.Collectors;

public interface AbstractMapper<E, M> {
    M toModel(E entity);
    E toEntity(M model);

    default List<M> toModelList(List<E> entities) {
        return entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    default List<E> toEntityList(List<M> models) {
        return models.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
