package mate.academy.rickandmorty.service.impl;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.dto.external.ResponseDto;
import mate.academy.rickandmorty.mapper.CharacterResponseMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterApiService;
import mate.academy.rickandmorty.service.CharacterInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterInitServiceImpl implements CharacterInitService {
    private static final String URL = "https://rickandmortyapi.com/api/character";

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterResponseMapper characterResponseMapper;

    @Autowired
    private CharacterApiService characterApiService;

    @Override
    public void init() {
        long characterCount = characterRepository.count();

        if (characterCount > 0) {
            return;
        }

        int pagesNumber = getNumberOfPages();

        for (int i = 0; i < pagesNumber; i++) {
            List<CharacterDto> charactersDto = getCharactersDataByPage(i + 1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            List<Character> characters = charactersDto
                    .stream()
                    .map(characterResponseMapper::toModel)
                    .toList();

            characterRepository.saveAll(characters);
        }
    }

    private int getNumberOfPages() {
        ResponseDto responseDto = characterApiService.fetchData(URL);

        return responseDto
                .getInfo()
                .getPages();
    }

    private List<CharacterDto> getCharactersDataByPage(int page) {
        ResponseDto responseDto = characterApiService.fetchData(URL + "/?page=" + page);

        return responseDto
                .getResults();
    }
}
