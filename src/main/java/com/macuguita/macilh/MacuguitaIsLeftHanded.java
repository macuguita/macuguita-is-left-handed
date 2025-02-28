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
		// Get the player's UUID safely
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.getSession() != null) {
			try {
				// Check for the correct method to retrieve UUID
				String uuidString = String.valueOf(client.getSession().getUuidOrNull());

				if (uuidString == null || uuidString.isEmpty()) {
					LOGGER.error("Failed to retrieve player UUID.");
					return;
				}

				// Convert UUID format correctly
				UUID playerUUID = UUID.fromString(String.valueOf(client.getSession().getUuidOrNull()));

				if (MACUGUITA_UUID.equals(playerUUID)) {
					LOGGER.info("Player is macuguita! Applying left-handed default.");
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid UUID format or missing UUID.");
			}
		} else {
			LOGGER.warn("Could not retrieve player UUID - session is null.");
		}
	}
}
