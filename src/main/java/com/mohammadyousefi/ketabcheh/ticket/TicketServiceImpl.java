package com.mohammadyousefi.ketabcheh.ticket;

import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket findById(Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("ticket"));
        return ticketOptional.get();
    }

    @Override
    public List<Ticket> findForAdmins() {
        Optional<List<Ticket>> optionalTickets = ticketRepository.findByAdminIsNull();
        if (optionalTickets.isEmpty()) return new ArrayList<>();
        return optionalTickets.get();
    }

    @Override
    public String save(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        User user = new User();
        user.setId(ticketDto.getUserId());
        ticket.setUser(user);
        ticket.setTitle(ticketDto.getTitle());
        ticketRepository.save(ticket);
        return "successfully added";
    }

    @Override
    public String addAdmin(Long adminId, Long ticketId) {
        Ticket ticket = findById(ticketId);
        User admin = new User();
        admin.setId(adminId);
        ticket.setAdmin(admin);
        ticketRepository.save(ticket);
        return "successfully added";
    }

    @Override
    public TicketMessage addMessage(TicketMessageDto ticketMessageDto) {
        Ticket ticket = findById(ticketMessageDto.getTicketId());
        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setTicket(ticket);
        ticketMessage.setMessage(ticketMessageDto.getMessage());
        User user = new User();
        user.setId(ticketMessageDto.getUserId());
        ticketMessage.setUser(user);
        if (ticketMessageDto.getReplyTo() != null) {
            TicketMessage repliedMessage = new TicketMessage();
            repliedMessage.setId(ticketMessageDto.getReplyTo());
            ticketMessage.setReplyTo(repliedMessage);
        }
        List<TicketMessage> messages;
        if (ticket.getMessages() != null) messages = ticket.getMessages();
        else messages = new ArrayList<>();
        messages.add(ticketMessage);
        ticketRepository.save(ticket);
        return ticketMessage;
    }

    @Override
    public String toggleTicketStatus(Long id) {
        Ticket ticket = findById(id);
        ticket.setIsOpen(!ticket.getIsOpen());
        ticketRepository.save(ticket);
        return "toggled successfully";
    }
}
