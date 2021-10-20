package com.nology.charactersAPI.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nology.charactersAPI.exceptions.MarvelApiException;
import com.nology.charactersAPI.models.MarvelCharacter;
import com.nology.charactersAPI.models.MarvelThumbnail;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Note: This is a singleton by default. More: https://stackoverflow.com/questions/5907501/when-annotating-a-class-with-component-does-this-mean-it-is-a-spring-bean-and).
@Component("marvelService")
public class MarvelService  {

    private final List<MarvelCharacter> characters = new ArrayList<MarvelCharacter>();
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    public MarvelService() throws MarvelApiException {
        try {
            this.seedCharacters();
        } catch (Exception e) {
            throw new MarvelApiException("Unable to seed characters. Exiting program...");
        }
    }

    /**
     * Retrieves ALL character Ids from store of characters
     * @return Character Ids | Empty array if no characters
     */
    public List<Integer> getCharacterIds() {
        return this.characters
                    .stream()
                    .map(MarvelCharacter::getId)
                    .collect(Collectors.toList());
    }

    /**
     * Retrieves Character by Id from store of characters
     * @param id - Id we're looking for
     * @return Character if found | null if not found
     */
    public MarvelCharacter getCharacterById(Integer id) {
        return this.characters.stream()
                .filter(character -> id.equals(character.getId()))
                .findAny()
                .orElse(null);
    }

    /**
     * Retrieves Raw Json Data from Marvel API and deserializes this into Characters and Thumbnails
     *
     * @throws Exception - Uncaught Exceptions seeding data from external API
     */
    private void seedCharacters() throws Exception {
        // TODO: Replace with key storage
        String privateKey = "35c056be1ef83c917196c541cf9a9b457a42b907";
        String publicKey = "2aed9dea9167a63fbe04748ce74e6903";
        String location = "/v1/public/comics?ts=1&apikey=" + publicKey + "&hash=181056474918153f3295d70be9a50e55";

        try {
            JsonObject rootobj = JsonWebService.getJsonObjectFromUrl("http://gateway.marvel.com" + location);

            // 1. Drill into nested keyvalues to retrieve list of characters
            JsonObject title = rootobj.get("data").getAsJsonObject();
            JsonArray results = title.get("results").getAsJsonArray();

            results.forEach(c -> {
                JsonObject characterJson = c.getAsJsonObject();

                // 1.1 Create the thumbnail
                JsonObject tJson = characterJson.get("thumbnail").getAsJsonObject();
                MarvelThumbnail thumb = new MarvelThumbnail(
                    JsonMapperService.getStringFromJson(tJson, "path"),
                    JsonMapperService.getStringFromJson(tJson, "extension")
                );

                // 1.2. Create our new character
                MarvelCharacter character = new MarvelCharacter(
                    JsonMapperService.getIntFromJson(characterJson, "id"),
                    JsonMapperService.getStringFromJson(characterJson, "title"),
                    JsonMapperService.getStringFromJson(characterJson, "description"),
                    thumb
                );

                // 1.3. Seed the character
                this.characters.add(character);
            });

            System.out.println(title);
        } catch (Exception e) {
            throw new Exception("Error seeding characters from Marvel API.");
        }
    }
}
