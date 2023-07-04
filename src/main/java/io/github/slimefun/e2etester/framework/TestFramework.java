package io.github.slimefun.e2etester.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import io.github.slimefun.e2etester.framework.annotations.E2ETest;
import io.github.slimefun.e2etester.framework.exceptions.TestFailException;
import io.github.slimefun.e2etester.tests.Startup;

public class TestFramework {

	private final Map<String, ArrayList<Consumer<Void>>> eventListeners = new HashMap<>();

	private int testsRan;
	private int testsPassed;
	private int testsFailed;
	private int testsSkipped;

	private TestFramework() {}

	public static TestFramework newTestRun() {
		return new TestFramework();
	}

	public void runTests(@Nonnull String packageName) {
		// TEMP HACK
		// TODO: Remove
		new Startup();

		// Reflection go through all classes
		Reflections reflections = new Reflections(ConfigurationBuilder
			.build()
			.addScanners(Scanners.values())
			.forPackage(packageName, getClass().getClassLoader())
		);

		logMessage("Running tests...");
		logMessage("");

		final Set<Method> testMethods = reflections.get(Scanners.MethodsAnnotated.with(E2ETest.class).as(Method.class));
		final Map<Class<?>, List<Method>> tests = orderTests(testMethods);
		for (Map.Entry<Class<?>, List<Method>> entry : tests.entrySet()) {
			logMessage("%s:", entry.getKey().getName());

			for (Method method : entry.getValue()) {
				String description = method.getAnnotation(E2ETest.class).description();

				// Invoke
				try {
					testsRan++;

					method.setAccessible(true);
					Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
					method.invoke(instance);

					testsPassed++;
					logMessage("  ✔ %s", description);
				} catch(TestFailException e) {
					testsFailed++;
					logMessage("  x %s", description);
					e.printStackTrace();
				} catch(Exception e) {
					testsFailed++;
					logMessage("  x %s", description);
					e.printStackTrace();
				}
			}
		}

		logMessage("");
		logMessage("Test results:");
		logMessage("  Tests ran: %d", this.testsRan);
		logMessage("  Tests passed: %d", this.testsPassed);
		logMessage("  Tests failed: %d", this.testsFailed);
		logMessage("  Tests skipped: %d", this.testsSkipped);

		if (this.testsFailed > 0) {
			logMessage("Test failure, exiting with code 1");
			System.exit(1);
		} else {
			Bukkit.shutdown();
		}
	}

	public void on(@Nonnull String event, Consumer<Void> consumer) {
		eventListeners.putIfAbsent(event, new ArrayList<Consumer<Void>>());

		ArrayList<Consumer<Void>> value = eventListeners.get(event);
		if (value != null) {
			value.add(consumer);
		} else {
			value = new ArrayList<>();
			value.add(consumer);
		}
		eventListeners.put(event, value);
	}

	public int getTestsRan() {
		return this.testsRan;
	}

	public int getTestsPassed() {
		return this.testsPassed;
	}

	public int getTestsFailed() {
		return this.testsFailed;
	}

	public int getTestsSkipped() {
		return this.testsSkipped;
	}

	private void logMessage(String str, Object... objects) {
		Bukkit.getConsoleSender().sendMessage(String.format(str, objects));
	}

	private Map<Class<?>, List<Method>> orderTests(Set<Method> methods) {
		final Map<Class<?>, List<Method>> tests = new LinkedHashMap<>();

		for (Method method : methods) {
			List<Method> list = tests.get(method.getDeclaringClass());
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(method);

			tests.put(method.getDeclaringClass(), list);
		}

		// Sort
		return tests.entrySet().stream()
			.sorted((entry1, entry2) -> entry1.getKey().getName().compareTo(entry2.getKey().getName()))
			.collect(Collectors.toMap(
				entry -> entry.getKey(),
				value -> value.getValue().stream()
					.sorted((method1, method2) -> method1.getName().compareTo(method2.getName()))
					.toList()
			));
	}
}
