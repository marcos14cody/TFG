package autoUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Main {
    private static final Path SOURCE_LOCATION = Paths.get("./src/main");
    private static final Path PROCESSOR_LOCATION = Paths.get("./src/processors");
	

	public static void main(String[] args) {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			SOURCE_LOCATION.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
			PROCESSOR_LOCATION.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey watchKey = null;

            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    if (event.context().toString().contains(".java")) {
                    	System.out.println("Compiling...");
                    	Process p = Runtime.getRuntime().exec("compile.bat");
                    	BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line;
                        while ((line = r.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                    
                }
                watchKey.reset();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
	}

}
