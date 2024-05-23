package org.fullstack.verry.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack.verry.domain.MemberEntity;
import org.fullstack.verry.dto.MemberDTO;
import org.fullstack.verry.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberServiceIf {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    @Override
    public int join(MemberDTO memberDTO) {
        MemberEntity member = modelMapper.map(memberDTO, MemberEntity.class);
        int id = memberRepository.save(member).getMemberIdx();
        return id;
    }

    @Override
    public MemberDTO memberinfo(String memberId) {
        MemberDTO memberinfo = modelMapper.map(memberRepository.findByMemberId(memberId), MemberDTO.class);
        return memberinfo;
    }
    @Override
    public int membercount(String memberId){
        int membercount = memberRepository.countByMemberId(memberId);
        return membercount;
    }
}
