package com.meteor.extrabotany.common.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler {

    public static class Client {

        public Client(ForgeConfigSpec.Builder builder) {

        }

    }

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue spawnWithMedal;
        public final ForgeConfigSpec.IntValue soundInterval;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("common");
            spawnWithMedal = builder
                    .comment("Whether players will be rewarded with a Paimon Medal when they first join the world. Default is true.")
                    .define("spawnWithMedal", true);

            soundInterval = builder
                    .comment("The interval of Paimon's speech. Default is 1200 ticks.")
                    .defineInRange("soundInterval", 1200, 0, Integer.MAX_VALUE);

            builder.pop();
        }

    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }


}
