package org.fullstack.verry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.TradeEntity;
import org.fullstack.verry.dto.BoardDTO;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeDTO;
import org.fullstack.verry.repository.TradeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final ModelMapper modelMapper;

    @Override
    public int regist(TradeDTO tradeDTO) {
        TradeEntity trade = modelMapper.map(tradeDTO, TradeEntity.class);

        int idx = tradeRepository.save(trade).getTradeIdx();

        return idx;
    }

    @Override
    public PageResponseDTO<TradeDTO> list(PageRequestDTO pageRequestDTO) {
        PageRequest pageable = pageRequestDTO.getPageable();

//        Page<TradeEntity> result = tradeRepository.findAll(PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getPage_size(), Sort.by("tradeIdx").descending()));
        Page<TradeEntity> result = tradeRepository.findAll(pageable);

//        log.info("result : {}", result.getContent());
        log.info("result : {}", result.getContent());

        List<TradeDTO> dtoList = result.getContent().stream()
                .map(trade -> modelMapper.map(trade, TradeDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<TradeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
//        return null;
    }
}
