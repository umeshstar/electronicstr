package com.bikkadit.electronicstroe.helper;

import com.bikkadit.electronicstroe.dtos.UserDto;
import com.bikkadit.electronicstroe.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PHelper {
    public static <U,V>PageableResponse<V>getPageableResponse(Page<U> page,Class<V> type){
        log.info("phelper -getPageableResponse method is start");
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response= new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber()+1);
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        log.info("phelper -getPageableResponse method is end");
        return response;
    }
}
