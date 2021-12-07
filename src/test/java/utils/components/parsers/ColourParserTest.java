package utils.components.parsers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.utils.component.parsers.colour.ComponentColourParser;

public class ColourParserTest {

    @Test
    public void onClearTest() {
        //SETUP
        String testAgainst = "";
        Component plainComponent = Component.text(testAgainst);

        //ACT
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ComponentColourParser().withTag(testAgainst, plainComponent));

        //ASSERT
    }

    @Test
    public void onHasTagTest() {
        //SETUP
        String testAgainst = "<color name=RED>Test";
        Component plainComponent = Component.text("Test");

        //ACT
        Component result = new ComponentColourParser().withTag(testAgainst, plainComponent);

        //ASSERT
        Assertions.assertNotNull(result.color());
        Assertions.assertEquals(NamedTextColor.RED, result.color());
    }

    @Test
    public void onHasTagEngTest() {
        //SETUP
        String testAgainst = "<colour name=RED>Test";
        Component plainComponent = Component.text("Test");

        //ACT
        Component result = new ComponentColourParser().withTag(testAgainst, plainComponent);

        //ASSERT
        Assertions.assertNotNull(result.color());
        Assertions.assertEquals(NamedTextColor.RED, result.color());
    }

    @Test
    public void onHasTagInvalidTest() {
        //SETUP
        String testAgainst = "<invalid name=RED>Test";
        Component plainComponent = Component.text("Test");

        //ACT
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ComponentColourParser().withTag(testAgainst, plainComponent));
    }
}
