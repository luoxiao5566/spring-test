package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Trade;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.RequestNotValidException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
public class RsController {
  @Autowired RsEventRepository rsEventRepository;
  @Autowired UserRepository userRepository;
  @Autowired RsService rsService;

  @GetMapping("/rs/list")
  public ResponseEntity<List<RsEvent>> getRsEventListBetween(
      @RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    List<RsEvent> rsEvents = rsService.getEventList(start,end);
    return ResponseEntity.ok(rsEvents);
  }

  @GetMapping("/rs/{index}")
  public ResponseEntity<RsEvent> getRsEvent(@PathVariable int index) {
    RsEvent rsEvent = rsService.getEventByIndex(index);
    return ResponseEntity.ok(rsEvent);
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    rsService.postEvent(rsEvent);
    return ResponseEntity.created(null).build();
  }

  @PostMapping("/rs/vote/{id}")
  public ResponseEntity vote(@PathVariable int id, @RequestBody Vote vote) {
    rsService.vote(vote, id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/rs/buy/{id}")
  public ResponseEntity buy(@PathVariable int id, @RequestBody Trade trade){
    rsService.buy(trade, id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/rs/{index}")
  public ResponseEntity deleteRsEvent(@PathVariable int index) {
    rsService.deleteEventByIndex(index);
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler(RequestNotValidException.class)
  public ResponseEntity<Error> handleRequestErrorHandler(RequestNotValidException e) {
    Error error = new Error();
    error.setError(e.getMessage());
    return ResponseEntity.badRequest().body(error);
  }
}
