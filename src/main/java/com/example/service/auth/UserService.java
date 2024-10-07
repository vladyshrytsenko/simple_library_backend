package com.example.service.auth;

import com.example.model.dto.UserDto;
import com.example.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EntityManager em;

    @Transactional
    public UserDto save(User user) {
        em.persist(user);
        return UserDto.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(cb.equal(root.get("email"), email));

        User found = em.createQuery(query).getSingleResult();

        if (found == null) {
            throw new RuntimeException("User not found!");
        }

        return UserDto.toDto(found);
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(cb.equal(root.get("username"), username));

        User found = em.createQuery(query).getSingleResult();

        if (found == null) {
            throw new RuntimeException("User not found!");
        }

        return UserDto.toDto(found);
    }
}
