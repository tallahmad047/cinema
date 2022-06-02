package org.sid.cinema.entities;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.config.Projection;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
@CrossOrigin("*")
@Projection(name="ticketProj",types=Ticket.class)
public interface TicketProjectio {
	public Long getId();
	public String getnomClient();
	public double getPrix();
	public Integer getcodePayement();
	public Boolean getreserve();
	public Place getplace();
}
