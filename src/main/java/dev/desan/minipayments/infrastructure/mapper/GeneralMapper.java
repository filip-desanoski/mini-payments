package dev.desan.minipayments.infrastructure.mapper;

public interface GeneralMapper<D, E> {
    D entityToDto(E e);

    E dtoToEntity(D d);
}
