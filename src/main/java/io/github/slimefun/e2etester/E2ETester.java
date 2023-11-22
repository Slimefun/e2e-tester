package io.github.slimefun.e2etester;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.slimefun.e2etester.framework.TestFramework;

public class E2ETester extends JavaPlugin {

	private static E2ETester instance;

	@Override
	public void onEnable() {
		instance = this;

		final TestFramework framework = TestFramework.newTestRun();

		// Wait a second for any startup stuff to be ran
		Bukkit.getScheduler().runTaskLater(this, () -> {
			framework.runTests("io.github.slimefun.e2etester.tests");
		}, 20);
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static E2ETester getInstance() {
		return instance;
	}
}
