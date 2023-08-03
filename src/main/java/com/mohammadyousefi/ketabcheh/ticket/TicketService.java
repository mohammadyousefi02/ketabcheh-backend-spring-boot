package com.mohammadyousefi.ketabcheh.ticket;

import java.util.List;

public interface TicketService {
    Ticket findById(Long id);

    List<Ticket> findForAdmins();

    String save(TicketDto ticketDto);

    String addAdmin(Long adminId, Long ticketId);

    TicketMessage addMessage(TicketMessageDto ticketMessageDto);

    String toggleTicketStatus(Long id);
}
