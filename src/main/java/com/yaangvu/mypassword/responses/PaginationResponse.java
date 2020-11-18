package com.yaangvu.mypassword.responses;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class PaginationResponse {
    
    private int size;
    
    private Long total;
    
    private int totalPage;
    
    private int page;
    
    private List<Object> data = new ArrayList<>();
    
    public <S, T> PaginationResponse handleList(@NotNull Page<S> source, Class<T> target) {
        ModelMapper mapper = new ModelMapper();
        PaginationResponse paginationResponse = new PaginationResponse();
        
        for (S s : source.getContent()) {
            data.add(mapper.map(s, target));
        }
        paginationResponse.setSize(source.getSize());
        paginationResponse.setPage(source.getNumber() + 1);
        paginationResponse.setTotalPage(source.getTotalPages());
        paginationResponse.setTotal(source.getTotalElements());
        paginationResponse.setData(data);
        
        log.info("response body: {}", paginationResponse);
        return paginationResponse;
    }
}
