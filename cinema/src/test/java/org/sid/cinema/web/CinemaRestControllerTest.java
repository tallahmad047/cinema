package org.sid.cinema.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

class CinemaRestControllerTest {
    @Mock
    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Before("")
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testImage() throws Exception {
        // Créer un film
        Film film = new Film();
        film.setTitre("Film Test");
        film.setPhoto("cv.jpg");
        filmRepository.save(film);

        // Ajouter une image dans le dossier /cinemas/images/
        File file = new File(System.getProperty("user.home")+"/cinemas/images/cv.jpg");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write("test image data".getBytes());
        outputStream.close();

        // Vérifier que l'image est récupérée correctement par l'API
        mockMvc.perform(get("/imageFilm/" + film.getId()))
                .andExpect(status().isOk());
                //.andExpect(content().contentType(MediaType.IMAGE_JPEG))
               // .andExpect(content().bytes("test image data".getBytes()));

    }


    @Test
    void payerTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        List<Long> ticketIds = Arrays.asList(1L, 2L);
        TicketFrom ticketFrom = new TicketFrom();
        ticketFrom.setNomClient("John Doe");
        ticketFrom.setCodePayement(123456);
        ticketFrom.setTickets(ticketIds);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket1));
        when(ticketRepository.findById(2L)).thenReturn(Optional.of(ticket2));

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/payerTickets")
                        .content(new ObjectMapper().writeValueAsString(ticketFrom))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        Mockito.verify(ticketRepository, times(2)).save(any(Ticket.class));
        assertTrue(ticket1.isReserve());
        assertTrue(ticket2.isReserve());
        assertEquals("John Doe", ticket1.getNomClient());
        assertEquals("John Doe", ticket2.getNomClient());
        assertEquals("123456", ticket1.getCodePayement());
        assertEquals("123456", ticket2.getCodePayement());
    }
}