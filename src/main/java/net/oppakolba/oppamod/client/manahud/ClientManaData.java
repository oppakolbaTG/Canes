package net.oppakolba.oppamod.client.manahud;

import lombok.Getter;
import lombok.Setter;

public class ClientManaData {
    @Getter
    @Setter
    private static int playerMana;
    public static final int INT = 20;
    @Getter
    @Setter
    private static int playerMaxMana = INT;
}
