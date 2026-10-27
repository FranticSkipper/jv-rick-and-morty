package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.config.MapperConfig;
import mate.academy.rickandmorty.dto.external.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CharacterResponseMapper {
    @Mapping(source = "id", target = "externalId")
    @Mapping(target = "id", ignore = true)
    Character toModel(CharacterDto characterDto);
}
