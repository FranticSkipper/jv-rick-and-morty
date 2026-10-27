package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character Management", description = "Endpoints for getting characters.")
@RestController
@RequestMapping("/character")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @Operation(
            summary = "Get random character.",
            description = "Retrieve a random character"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved a random character."
    )
    @GetMapping
    public Character getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @Operation(
            summary = "Get characters by name.",
            description = "Retrieve a list of characters whose name contain a search string "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved a list of characters."
    )
    @GetMapping("/{search}")
    public List<Character> getCharactersByName(@PathVariable String search) {
        return characterService.getCharactersByName(search);
    }
}
