package org.fullstack.verry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.TradeReplyEntity;
import org.fullstack.verry.dto.PageRequestDTO;
import org.fullstack.verry.dto.PageResponseDTO;
import org.fullstack.verry.dto.TradeReplyDTO;
import org.fullstack.verry.repository.TradeReplyRepository;
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
public class TradeReplyServiceImpl implements TradeReplyService {

    private final TradeReplyRepository tradeReplyRepository;
    private final ModelMapper modelMapper;

    @Override
    public int regist(TradeReplyDTO replyDTO) {
        TradeReplyEntity reply = modelMapper.map(replyDTO, TradeReplyEntity.class);

        int result = tradeReplyRepository.save(reply).getTradeReplyIdx();

        return result;
    }

    @Override
    public void delete(int idx) {
        tradeReplyRepository.deleteById(idx);
    }

    @Override
    public PageResponseDTO<TradeReplyDTO> getListOfReply(int trade_idx, PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(
                (pageRequestDTO.getPage() < 0 ? 0 : pageRequestDTO.getPage()-1),
                pageRequestDTO.getPage_size(),
                Sort.by("tradeReplyIdx").descending()
        );

        Page<TradeReplyEntity> result = tradeReplyRepository.listOfBoardReply(trade_idx, pageable);

        int total_count = (result != null ? (int) result.getTotalElements() : 0);

        List<TradeReplyDTO> dtoList = null;
        if(result != null) {
            dtoList = result.getContent().stream()
                    .map(reply -> modelMapper.map(reply, TradeReplyDTO.class))
                    .collect(Collectors.toList());
        }

        return PageResponseDTO.<TradeReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count(total_count)
                .build();
    }
}
