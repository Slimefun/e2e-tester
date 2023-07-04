package io.github.slimefun.e2etester.framework;

import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;

import io.github.slimefun.e2etester.framework.exceptions.TestFailException;
import io.github.slimefun.e2etester.framework.mock.MockConsoleSender;

public class Assert {

	public static void isTrue(boolean expression) {
		if (!expression) {
			throw new TestFailException("isTrue expected true but got false");
		}
	}

	public static void runConsoleCommand(String command, Predicate<String> output) {
		try {
			final MockConsoleSender sender = new MockConsoleSender();

			final boolean commandExists = Bukkit.dispatchCommand(sender, command);
			if (!commandExists) {
				throw new TestFailException("Comamnd '" + command + "' does not exist!");
			}

			final String commandOutput = String.join("\n", sender.getMessages());
			if (!output.test(commandOutput)) {
				throw new TestFailException("Output was not as expected! Output: " + commandOutput);
			}
		} catch(CommandException e) {
			throw new TestFailException("Command '" + command + "' threw an unexpected exception!", e);
		}
	}
}
