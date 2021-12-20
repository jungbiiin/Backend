package com.village.soonyee.repository;

import com.village.soonyee.domain.Diary;
import com.village.soonyee.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class DiaryRepository {
    @PersistenceContext
    private EntityManager em;
    public Long save(Diary diary){
        em.persist(diary);
        return diary.getId();
    }
    @Transactional(readOnly = true)
    public List<Diary> findByMember(Member member){
        return em.createQuery("select m from Diary m where m.writer=:member",Diary.class)
                .setParameter("member",member)
                .getResultList();
    }
    @Transactional(readOnly = true)
    public Diary findById(Long id){
        return em.find(Diary.class,id);
    }
}
