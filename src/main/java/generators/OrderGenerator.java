package generators;

import models.OrderRequest;
import java.util.List;

import static utils.Utils.randomString;
public class OrderGenerator {
    public static OrderRequest randomOrderOneColor() {
        return new OrderRequest()
                .withFirstName(randomString(5))
                .withLastName(randomString(8))
                .withAddress(randomString(15))
                .withMetroStation("3")
                .withPhone("+7 911 546 34 67")
                .withRenTime(4)
                .withDeliveryDate("2024-10-10")
                .withComment("call before delivery")
                .withColor(List.of("black"));
    }

    public static OrderRequest randomOrderTwoColors(List<String> list) {
        return new OrderRequest()
                .withFirstName(randomString(5))
                .withLastName(randomString(8))
                .withAddress(randomString(15))
                .withMetroStation("3")
                .withPhone("+7 911 546 34 67")
                .withRenTime(4)
                .withDeliveryDate("2024-10-10")
                .withComment("call before delivery")
                .withColor(list);
    }
}
