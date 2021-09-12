package com.meteor.extrabotany.common.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHandler {

    public static class Client {

        public final ForgeConfigSpec.BooleanValue disablelogInfo;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("client");
            disablelogInfo = builder
                    .comment("Whether to disable the spam in the logs. Default is false.")
                    .define("disableLogSpam", false);
            builder.pop();
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

        public final ForgeConfigSpec.BooleanValue disableDisarm;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("common");
            disableDisarm = builder
                    .comment("Whether to disable the Ego's disarm. Default is false.")
                    .define("disableDisarm", false);
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
