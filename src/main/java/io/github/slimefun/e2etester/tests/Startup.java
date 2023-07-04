package io.github.slimefun.e2etester.tests;

import org.bukkit.Bukkit;

import io.github.slimefun.e2etester.framework.Assert;
import io.github.slimefun.e2etester.framework.annotations.E2ETest;

public class Startup {

	@E2ETest(description = "Test that Slimefun gets enabled")
	public void testPluginIsEnabled() {
		// Ensure the plugin started
		Assert.isTrue(Bukkit.getPluginManager().isPluginEnabled("Slimefun"));
	}

	@E2ETest(description =  "Test that `/sf versions` runs successfully")
	public void testVersionsCommandCanBeRun() {
		// Run /sf versions and make sure it doesn't error
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Slimefun 4.9-UNOFFICIAL"));
	}
}
