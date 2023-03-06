package org.sid.cinema.dao;


import org.sid.cinema.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findById(long user_id);

    Utilisateur findByLoginAndPwd(String login, String pwd);
}

