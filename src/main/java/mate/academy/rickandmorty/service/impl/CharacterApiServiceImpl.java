package mate.academy.rickandmorty.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import mate.academy.rickandmorty.dto.external.ResponseDto;
import mate.academy.rickandmorty.service.CharacterApiService;
import org.springframework.stereotype.Service;

@Service
public class CharacterApiServiceImpl implements CharacterApiService {
    @Override
    public ResponseDto fetchData(String url) {
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(url))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new RuntimeException("API returned status " + response.statusCode()
                        + " for url=" + url + ", body=" + response.body());
            }

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(
                    response.body(),
                    ResponseDto.class
            );

        } catch (URISyntaxException | IOException | InterruptedException | RuntimeException ex) {
            throw new RuntimeException("Cannot fetch data from API. url= " + url, ex);
        }
    }
}
