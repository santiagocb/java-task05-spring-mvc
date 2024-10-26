package com.ticketland.oxm;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public TicketXML createTicket() {
        return new TicketXML();
    }

    public TicketsXML createTicketsWrapper() {
        return new TicketsXML();
    }
}
