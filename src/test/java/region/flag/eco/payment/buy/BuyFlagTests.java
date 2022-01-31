package region.flag.eco.payment.buy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zone.region.flag.meta.eco.payment.buy.BuyFlag;
import org.zone.region.flag.meta.eco.price.Price;
import org.zone.region.flag.meta.eco.price.PriceType;
import org.zone.region.flag.meta.eco.price.player.PlayerExpPrice;
import tools.configuration.FlagLoadTester;

import java.io.IOException;

public class BuyFlagTests {

    @Test
    public void testBuySaveNull() {
        BuyFlag flag = new BuyFlag(new PlayerExpPrice(20));

        try {
            if (!FlagLoadTester.testNull(flag)) {
                Assertions.fail("Did not remove children");
            }
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testBuySave() {
        BuyFlag flag = new BuyFlag(new PlayerExpPrice(20));

        try {
            FlagLoadTester.testSave(flag);
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }

    @Test
    public void testBuyLoad() {
        BuyFlag flag = new BuyFlag(new PlayerExpPrice(20));

        try {
            BuyFlag loaded = FlagLoadTester.testLoad(flag);

            Price.PlayerPrice<? extends Number> price = loaded.getPrice();
            Assertions.assertEquals(20, price.getAmount());
            Assertions.assertEquals(PriceType.EXP, price.getType());
            Assertions.assertEquals(PlayerExpPrice.class.getName(), price.getClass().getName());
        } catch (IOException e) {
            Assertions.fail("Could not save", e);
        }
    }
}
