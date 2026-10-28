package mate.academy.rickandmorty.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public Character getRandomCharacter() {
        List<Long> ids = characterRepository
                .findAll()
                .stream()
                .map(Character::getId)
                .toList();

        if (ids.isEmpty()) {
            throw new EntityNotFoundException("A list of characters is empty");
        }

        Random random = new Random();
        long randomId = ids.get(random.nextInt(ids.size()) + 1);

        return characterRepository
                .findById(randomId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Character is not found. "
                                + "Id= " + randomId));
    }

    @Override
    public List<Character> getCharactersByName(String search) {
        return characterRepository.findAllBySearchStringContaining(search);
    }
}
