package org.fullstack.verry.service;

import org.fullstack.verry.dto.MemberDTO;

public interface MemberServiceIf {
    int join(MemberDTO memberDTO);
    MemberDTO memberinfo(String memberId);
}
