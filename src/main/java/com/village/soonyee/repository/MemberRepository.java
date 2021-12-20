package com.village.soonyee.repository;

import com.village.soonyee.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MemberRepository {
    @PersistenceContext
    EntityManager em;
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }
    public void updateMessage(Member member,String message){
        em.createQuery("update Member m set m.statusMessage=:message where m=:member")
                .setParameter("message",message)
                .setParameter("member",member)
                .executeUpdate();
    }
    public void updateProfile(String fileName,Member member){
        em.createQuery("update Member m set m.profile=:fileName where m=:member")
                .setParameter("fileName",fileName)
                .setParameter("member",member)
                .executeUpdate();
    }
    @Transactional(readOnly = true)
    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email=:email",Member.class)
                .setParameter("email",email)
                .getResultList();
    }
    @Transactional(readOnly = true)
    public Member findById(Long memberIdx){
        return em.find(Member.class,memberIdx);
    }
}
