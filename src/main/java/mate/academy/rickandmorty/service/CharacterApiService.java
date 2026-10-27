package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.external.ResponseDto;

public interface CharacterApiService {
    ResponseDto fetchData(String url);
}
