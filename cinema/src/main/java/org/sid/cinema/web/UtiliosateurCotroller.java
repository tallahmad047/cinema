package org.sid.cinema.web;


import org.sid.cinema.dao.UtilisateurRepository;
import org.sid.cinema.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController

public class UtiliosateurCotroller {
    @Autowired
    private UtilisateurRepository usersRepository;

    @PostMapping(path = "/utilisateurs")
    public ResponseEntity<Object> add(@RequestBody Utilisateur users) {

        Utilisateur users1 = usersRepository.save(users);

        if (users1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(users1.getUser_id())
                .toUri();

        return ResponseEntity.status(201).body(users1);
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<Object> login(@RequestBody Utilisateur users) {
        Utilisateur users1 = usersRepository.findByLoginAndPwd(users.getLogin(), users.getPwd());
        if (users == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Cette utilisateur n'existe pas !'}");

        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/rvs/{id}/users")
                .buildAndExpand(users1.getUser_id())
                .toUri();

      //  users1.setRv_list_url(location.toString());

        return ResponseEntity.ok(users1);
    }

    @PutMapping(value = "/users/{user_id}")
    public ResponseEntity<Object> update(@PathVariable long user_id, @RequestBody Utilisateur users) {

        Utilisateur users1 = usersRepository.findById(user_id);

        if (users1 == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Cet utilisateur n'existe pas !'}");

        users.setUser_id(users1.getUser_id());
        users1 = usersRepository.save(users);
        if (users1 == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(users1.getUser_id())
                .toUri();

        return ResponseEntity.ok(location);
    }

    @GetMapping(value = "/users")
    public List<Utilisateur> getAll() {
        return usersRepository.findAll();
    }

    @GetMapping(value = "/users/{user_id}")
    public ResponseEntity<Object> getAll(@PathVariable long user_id) {
        Utilisateur users = usersRepository.findById(user_id);
        if (users == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{'Erreur': 'Cette utilisateur n'existe pas !'}");


        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/rvs/{id}/users")
                .buildAndExpand(users.getUser_id())
                .toUri();

       // users.setRv_list_url(location.toString());

        return ResponseEntity.ok(users);
    }
}
