package io.github.slimefun.e2etester.tests;

import org.bukkit.Bukkit;

import io.github.slimefun.e2etester.framework.Assert;
import io.github.slimefun.e2etester.framework.annotations.E2ETest;

public class VersionsTest {

	@E2ETest(description = "Test that `/sf versions` returns the right MC version")
	public void testMcVersion() {
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Paper " + Bukkit.getVersion()));
	}

	@E2ETest(description = "Test that `/sf versions` returns the right SF version")
	public void testSlimefunVersion() {
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Slimefun Preview Build"));
	}

	@E2ETest(description = "Test that `/sf versions` returns the right Java version")
	public void testJavaVersion() {
		// Grab the Java version, if it's still a 1.x strip that and then remove minor so we go from 1.8.1 -> 8
		String expectedVersion = System.getProperty("java.version");
		if (expectedVersion.startsWith("1.")) {
			expectedVersion = expectedVersion.substring(2);
		}
		int minorVersionIdx = expectedVersion.indexOf('.');
		if (minorVersionIdx != -1) {
			expectedVersion = expectedVersion.substring(0, minorVersionIdx);
		}

		final String finalExpectedVersion = expectedVersion;
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Java " + finalExpectedVersion));
	}
}
