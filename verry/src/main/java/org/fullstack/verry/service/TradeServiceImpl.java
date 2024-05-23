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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
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
        PageRequest pageable = pageRequestDTO.getPageable("tradeIdx");

        Page<TradeEntity> result = tradeRepository.findAll(pageable);

        List<TradeDTO> dtoList = result.getContent().stream()
                .map(trade -> modelMapper.map(trade, TradeDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<TradeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }

    @Override
    public TradeDTO view(int trade_idx) {
        Optional<TradeEntity> result = tradeRepository.findById(trade_idx);

        TradeEntity trade = result.orElse(null);

        TradeDTO tradeDTO = modelMapper.map(trade, TradeDTO.class);

        return tradeDTO;
    }

    @Override
    public List<TradeDTO> relatedProducts(String category, int trade_idx) {
        // 관련상품 리스트
        List<TradeEntity> list = tradeRepository.findDistinctTop4ByCategoryAndTradeIdxNotOrderByTradeIdxDesc(category, trade_idx);

        List<TradeDTO> relatedList = list.stream().map(trade -> modelMapper.map(trade, TradeDTO.class)).collect(Collectors.toList());

        return relatedList;
    }

    @Override
    public int modify(TradeDTO tradeDTO) {
        Optional<TradeEntity> result = tradeRepository.findById(tradeDTO.getTradeIdx());
        TradeEntity trade = result.orElse(null);
        trade.modify(tradeDTO.getTitle(), tradeDTO.getContent(), tradeDTO.getOrgFileName(), tradeDTO.getSaveFileName(), tradeDTO.getCategory(), tradeDTO.getPrice());

        int tradeIdx = tradeRepository.save(trade).getTradeIdx();

        return tradeIdx;
    }

    @Override
    public void deleteOne(int trade_idx) {
        tradeRepository.deleteById(trade_idx);
    }


    // 메인페이지 리스트
    @Override
    public PageResponseDTO<TradeDTO> mainShoplist(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage_size(8);
        PageRequest pageable = pageRequestDTO.getPageable();

        Page<TradeEntity> result = tradeRepository.findAll(pageable);

        log.info("result : {}", result.getContent());

        List<TradeDTO> dtoList = result.getContent().stream()
                .map(trade -> modelMapper.map(trade, TradeDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<TradeDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }

    @Override
    public List<TradeDTO> mainCategoryList(String category) {
        List<TradeEntity> list = tradeRepository.findDistinctTop8ByCategoryOrderByTradeIdxDesc(category);
        List<TradeDTO> categoryList = list.stream().map(trade -> modelMapper.map(trade, TradeDTO.class)).collect(Collectors.toList());

        return categoryList;
    }
}
