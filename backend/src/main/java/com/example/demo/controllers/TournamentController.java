package com.example.demo.controllers;

import com.example.demo.dtos.TournamentDTO;
import com.example.demo.services.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentController {
    @Autowired
    private TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(
            @RequestPart("tournament") TournamentDTO tournamentDTO,
            @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentDTO, image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<TournamentDTO> updateTournament(
            @PathVariable Long id,
            @RequestPart(value = "tournament", required = false) TournamentDTO tournamentDetails,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournamentDetails, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTournament(@PathVariable Long id) {
        String response = tournamentService.deleteTournament(id);
        return ResponseEntity.ok(response);
    }
}
