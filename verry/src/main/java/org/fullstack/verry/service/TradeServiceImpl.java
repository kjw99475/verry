package org.fullstack.verry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.TradeEntity;
import org.fullstack.verry.dto.TradeDTO;
import org.fullstack.verry.repository.TradeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
}
