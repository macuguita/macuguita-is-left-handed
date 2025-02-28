package com.macuguita.macilh.mixin;

import com.macuguita.macilh.MacuguitaIsLeftHanded;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.Arm;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(method = "<init>", at = @At("RETURN"))
	private void modifyDefaults(MinecraftClient client, java.io.File optionsFile, CallbackInfo ci) {
		if (client != null && client.getSession() != null) {
			try {
				// Convert UUID string (without dashes) into proper format
				UUID playerUUID = UUID.fromString(String.valueOf(client.getSession().getUuidOrNull()));

				if (MacuguitaIsLeftHanded.MACUGUITA_UUID.equals(playerUUID)) {
					GameOptions options = (GameOptions) (Object) this;

					// Set main hand to left
					options.getMainArm().setValue(Arm.LEFT);
					MacuguitaIsLeftHanded.LOGGER.info("Macuguita is now left-handed!");

					// Change Toggle Perspective key to backslash (\)
					options.togglePerspectiveKey.setBoundKey(InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_BACKSLASH));
					MacuguitaIsLeftHanded.LOGGER.info("Toggle Perspective key set to backslash for Macuguita.");
				}
			} catch (IllegalArgumentException e) {
				MacuguitaIsLeftHanded.LOGGER.error("Something went wrong... someone might not be left-handed.");
			}
		}
	}
}
