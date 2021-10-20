package com.nology.charactersAPI.controllers;

import com.nology.charactersAPI.models.MarvelCharacter;
import com.nology.charactersAPI.services.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharactersController {

    // 1. Why have I used @AutoWired rather than just creating a "new MarvelService()"?
    @Autowired
    MarvelService apiService;

    @GetMapping("characters")
    public ResponseEntity<List<Integer>> getCharacters() {
        List<Integer> response = apiService.getCharacterIds();
        return ResponseEntity.ok(response);
    }

    @GetMapping("characters/{id}")
    public ResponseEntity<MarvelCharacter> getCharacter(@PathVariable int id) {
        MarvelCharacter response = apiService.getCharacterById(id);
        return ResponseEntity.ok(response);
    }
}
