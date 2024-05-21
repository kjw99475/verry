package org.fullstack.verry.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
public class PageResponseDTO<E> {

    private int total_count;
    private PageRequestDTO pageRequestDTO;
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(int total_count, PageRequestDTO pageRequestDTO, List<E> dtoList, String type) {
        pageRequestDTO.setTotal_count(total_count < 0 ? 1 : total_count);
        pageRequestDTO.setPage_size(pageRequestDTO.getPage_size());
        pageRequestDTO.setTotal_page(pageRequestDTO.getTotal_page());
        pageRequestDTO.setPage_block_size(pageRequestDTO.getPage_block_size());
        pageRequestDTO.setPage_block_end(pageRequestDTO.getPage_block_end());
        pageRequestDTO.setPage_block_start(pageRequestDTO.getPage_block_start());
        pageRequestDTO.setPrev_page_flag(pageRequestDTO.getPrev_page_flag());
        pageRequestDTO.setNext_page_flag(pageRequestDTO.getNext_page_flag());

        this.pageRequestDTO = pageRequestDTO;
        this.total_count = total_count;
        this.dtoList = dtoList;
        pageRequestDTO.setType(pageRequestDTO.getType());
    }

}