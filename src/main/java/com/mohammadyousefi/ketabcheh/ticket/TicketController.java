package com.mohammadyousefi.ketabcheh.ticket;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public TicketController(TicketService ticketService, SimpMessagingTemplate simpMessagingTemplate) {
        this.ticketService = ticketService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    @Admin
    @Authorization
    public Response<List<Ticket>> findTickets() {
        return new Response<>(ticketService.findForAdmins());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    public Response<String> save(HttpServletRequest request, @RequestBody TicketDto ticketDto) {
        ticketDto.setUserId(getUserId(request));
        return new Response<>(ticketService.save(ticketDto), HttpStatus.CREATED.value());
    }

    @PostMapping("/add-admin/{ticketId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    @Admin
    public Response<String> save(HttpServletRequest request, @PathVariable Long ticketId) {
        Long adminId = getUserId(request);
        return new Response<>(ticketService.addAdmin(adminId, ticketId), HttpStatus.CREATED.value());
    }

    @PostMapping("/add-message")
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    public Response<TicketMessage> addMessage(HttpServletRequest request, @RequestBody TicketMessageDto ticketMessageDto) {
        ticketMessageDto.setUserId(getUserId(request));
        TicketMessage ticketMessage = ticketService.addMessage(ticketMessageDto);
        simpMessagingTemplate.convertAndSend("/topic/support/" + ticketMessageDto.getTicketId(), ticketMessage);
        return new Response<>(ticketMessage, HttpStatus.CREATED.value());
    }

    @PostMapping("/toggle-status/{ticketId}")
    @Authorization
    @Admin
    public Response<String> save(@PathVariable Long ticketId) {
        return new Response<>(ticketService.toggleTicketStatus(ticketId));
    }

    private Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }
}
