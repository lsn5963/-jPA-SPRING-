package jpabook.jpashop.service;

import jpabook.jpashop.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository; //final로 하는걸 권장

//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//        테스트케이스를 작성할 때 뭘 주입해야하는ㄴ지 잘 알 수 있다.
//    }

    //회원가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회(조회같은 읽기전용에서 readOnly는 트루한다.)
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
