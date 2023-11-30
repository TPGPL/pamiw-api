package pl.edu.pw.pamiwapi.mappers;

public interface EntityMapper<E, D> {
    D mapToDto(E entity);
    E mapToEntity(D dto);
}
