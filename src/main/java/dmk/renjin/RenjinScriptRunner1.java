package dmk.renjin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenjinScriptRunner1 {
	private Logger logger = LoggerFactory.getLogger(RenjinScriptRunner1.class);

	public static void main(String[] args) throws Exception {
		new RenjinScriptRunner1().test1();
		new RenjinScriptRunner1().test2();

	}

	public RenjinScriptRunner1() {
		super();
	}

	public void test1() {
		final String scriptName = "/df-lm-sample.R";
		this.runScript(scriptName);
	}
	
	public void test2() {
		final String scriptName = "/breakout.R";
		this.runScript(scriptName);
	}
	

	public void runScript(final String scriptName) {
		// create a script engine manager:
		ScriptEngineManager manager = new ScriptEngineManager();
		// create a Renjin engine:
		ScriptEngine engine = manager.getEngineByName("Renjin");
		// check if the engine has loaded correctly:
		if (engine == null) {
			throw new RuntimeException(
					"Renjin Script Engine not found on the classpath.");
		}

		try (Stream<String> lines = Files.readAllLines(
				Paths.get(this.getClass().getResource(scriptName).toURI()),
				Charset.defaultCharset()).stream()) {
			String script = lines.collect(Collectors.joining("\n"));
			logger.info(script);
			engine.eval(script);

		} catch (IOException | URISyntaxException | ScriptException e) {
			e.printStackTrace();
		}

	}

}