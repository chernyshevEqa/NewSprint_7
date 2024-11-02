package generators;

import models.Courier;

import static utils.Utils.randomString;

public class CourierGenerator {
    public static Courier randomCourier() {
        return new Courier()
                .withLogin(randomString(8))
                .withPassword(randomString(12))
                .withFirstName(randomString(20));
    }

    public static Courier randomCourierNoPassword() {
        return new Courier()
                .withLogin(randomString(8))
                .withFirstName(randomString(20));
    }
}
