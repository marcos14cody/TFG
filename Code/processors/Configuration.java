package processors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
	// Map of annotations and the list of annotations they depend on
	@SuppressWarnings("serial")
	public static Map<String, List<String>> DependencyTree = new HashMap<String, List<String>>() {{
		put("CD_OneToMany", Arrays.asList("CD_Class"));
		put("CD_Parameter", Arrays.asList("CD_Class"));
	}};
	
	// List of annotations and their associated processors
	@SuppressWarnings("serial")
	public static Map<String, Class<? extends Processor>> Processors = new HashMap<String, Class<? extends Processor>>() {{
		put("CD_Class", CD_Class_Processor.class);
		put("CD_OneToMany", CD_OneToMany_Processor.class);
		put("CD_Parameter", CD_Parameter_Processor.class);
	}};
	
	// Definition of main processor
	public static Class<? extends FinalProcessor> MainProcessor = CD_Main_Processor.class;
}
