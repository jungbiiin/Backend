package com.village.soonyee.repository;

import com.village.soonyee.domain.GuestBook;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GuestBookRepository {
    @PersistenceContext
    private EntityManager em;
    public Long save(GuestBook guestBook){
        em.persist(guestBook);
        return guestBook.getId();
    }
    @Transactional(readOnly = true)
    public List<GuestBook> findByTarget(Long memberId){
        return em.createQuery("select m from GuestBook m where m.target_id=:member_id",GuestBook.class)
                .setParameter("member_id",memberId)
                .getResultList();
    }
    @Transactional(readOnly = true)
    public GuestBook findById(Long guestBookIdx){
        return em.find(GuestBook.class,guestBookIdx);
    }
    public void deleteGuestBook(Long guestBookId){
        em.createQuery("delete from GuestBook g where g.id=:guestBookId")
                .setParameter("guestBookId",guestBookId)
                .executeUpdate();
    }
}
