package io.github.slimefun.e2etester.framework.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.chat.BaseComponent;

public class MockConsoleSender implements CommandSender {

	private final List<String> messages = new ArrayList<>();

	// Test functions
	public List<String> getMessages() {
		return this.messages;
	}

	// Override functions
	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public void setOp(boolean value) {}

	@Override
	public void sendMessage(String message) {
		this.messages.add(message);
	}

	@Override
	public void sendMessage(String[] messages) {
		this.messages.addAll(Arrays.asList(messages));
	}

	@Override
	public void sendMessage(UUID sender, String message) {
		this.messages.add(message);
	}

	@Override
	public void sendMessage(UUID sender, String[] messages) {
		this.messages.addAll(Arrays.asList(messages));
	}

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}

	@Override
	public String getName() {
		return "MockConsoleSender";
	}

	@Override
	public boolean isPermissionSet(String name) {
		return true;
	}

	@Override
	public boolean isPermissionSet(Permission perm) {
		return true;
	}

	@Override
	public boolean hasPermission(String name) {
		return true;
	}

	@Override
	public boolean hasPermission(Permission perm) {
		return true;
	}

	@Override
	public void recalculatePermissions() {}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return new HashSet<>();
	}

	// Not implemented
	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
		throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
		throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		throw new UnsupportedOperationException("Unimplemented method 'addAttachment'");
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		throw new UnsupportedOperationException("Unimplemented method 'removeAttachment'");
	}

	@Override
	public Spigot spigot() {
		return new Spigot() {
			@Override
			public void sendMessage(@Nonnull BaseComponent component) {
				messages.add(BaseComponent.toPlainText(component));
			}

			@Override
			public void sendMessage(@Nonnull BaseComponent... components) {
				messages.add(BaseComponent.toPlainText(components));
			}

			@Override
			public void sendMessage(@Nullable UUID sender, @Nonnull BaseComponent component) {
				messages.add(BaseComponent.toPlainText(component));
			}

			@Override
			public void sendMessage(@Nullable UUID sender, @Nonnull BaseComponent... components) {
				messages.add(BaseComponent.toPlainText(components));
			}
		};
	}
}
