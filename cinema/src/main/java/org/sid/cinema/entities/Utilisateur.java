package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;
    private String first_name;
    private String last_name;
    private String address;
    private Date birth_date;
    private int phone_number;
    private String login;
    private String pwd;
    private int profile;
}
