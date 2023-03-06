package org.sid.cinema;

import org.sid.cinema.dao.UtilisateurRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Utilisateur;
import org.sid.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.sid.cinema.entities.Ticket;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import sun.security.krb5.internal.Ticket;
//import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication  implements CommandLineRunner{
	@Autowired private ICinemaInitService cinemaInitService;
	@Autowired private RepositoryRestConfiguration restConfiguration;
	@Autowired private UtilisateurRepository utilisateurRepository;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	restConfiguration.exposeIdsFor(Film.class,Salle.class, Ticket.class);
		cinemaInitService.initVilles(); 
		cinemaInitService.initCinemas();
		cinemaInitService.initSalles(); 
		cinemaInitService.initPalces();
		cinemaInitService.initSeances(); 
		cinemaInitService.initCategories();
		cinemaInitService.initFilms();
		cinemaInitService.initProjections();
		cinemaInitService.initTickets();
		String dateString = "2002/02/15";
		LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		Date dateValue = Date.valueOf(date);


		Utilisateur utilsateur=new Utilisateur(1l,"tall","ahmad","gadaye", dateValue,784219485,"tall@ahmad","tall",1);
		utilisateurRepository.save(utilsateur);
		
	}

}
