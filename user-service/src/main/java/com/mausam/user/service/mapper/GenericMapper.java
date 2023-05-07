package com.mausam.user.service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public interface GenericMapper<E, D> {

    E convertToEntity(D dto);

    D convertToDTO(E entity);

    default List<E> convertToEntityList(List<D> dList) {
        List<E> eList = new ArrayList<>();
        eList = dList.stream().map(d -> convertToEntity(d)).collect(Collectors.toList());
        return eList;
    }

    default List<D> convertToDTOList(List<E> eList) {
        List<D> dList = new ArrayList<>();
        dList = eList.stream().map(e -> convertToDTO(e)).collect(Collectors.toList());
        return dList;
    }
}
