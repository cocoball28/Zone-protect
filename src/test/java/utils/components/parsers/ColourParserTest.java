package utils.components.parsers;

import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.utils.component.parsers.colour.ComponentColourParser;

public class ColourParserTest {

    private ColourParserTest() {
    }

    @Test
    public void onClearTest() {
        //SETUP
        String testAgainst = "";
        Component plainComponent = Component.text(testAgainst);

        //ACT
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ComponentColourParser().withTag(testAgainst, plainComponent));

        //ASSERT
    }
}
