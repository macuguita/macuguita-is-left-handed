package com.macuguita.macilh;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MacuguitaIsLeftHanded implements ClientModInitializer {
	public static final String MOD_ID = "macilh";
	public static final UUID MACUGUITA_UUID = UUID.fromString("0e56050b-ee27-478a-a345-d2b384919081");
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");

		// Get the player's UUID safely
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.getSession() != null) {
			try {
				// Convert session UUID (no dashes) into a proper format
				UUID playerUUID = UUID.fromString(client.getSession().getUuid().replaceFirst(
						"(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"
				));

				if (MACUGUITA_UUID.equals(playerUUID)) {
					LOGGER.info("Player is Macuguita! Applying left-handed default.");
				} else {
					LOGGER.info("Player is NOT Macuguita.");
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid UUID format: {}", client.getSession().getUuid(), e);
			}
		} else {
			LOGGER.warn("Could not retrieve player UUID - session is null.");
		}
	}
}
