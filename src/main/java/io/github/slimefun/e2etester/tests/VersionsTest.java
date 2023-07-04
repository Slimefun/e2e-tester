package io.github.slimefun.e2etester.tests;

import io.github.slimefun.e2etester.framework.Assert;
import io.github.slimefun.e2etester.framework.annotations.E2ETest;

public class VersionsTest {

	@E2ETest(description = "Test that `/sf versions` returns the right MC version")
	public void testMcVersion() {
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("MC: 1.20.1"));
	}

	@E2ETest(description = "Test that `/sf versions` returns the right SF version")
	public void testSlimefunVersion() {
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Slimefun 4.9-UNOFFICIAL"));
	}

	@E2ETest(description = "Test that `/sf versions` returns the right Java version")
	public void testJavaVersion() {
		Assert.runConsoleCommand("sf versions", (output) -> output.contains("Java 20"));
	}
}
