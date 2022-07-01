package processors;

import annotations.AnnotationInfo;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CD_Parameter_Processor extends Processor {
	public static Map<String, List<String>> outputInfo = new HashMap<>();
	
	@Override
	public boolean process(List<AnnotationInfo> infos) {
		for (AnnotationInfo info : infos) {
			String parentName = info.getParentName();
			if (!CD_Class_Processor.classNames.contains(parentName)) {
				System.out.println("No se puede añadir un parametro fuera de una clase. Parametro: " + info.getName() + ". Clase: " + parentName);
				return false;
			}
			if (!outputInfo.containsKey(parentName)) {
				outputInfo.put(parentName, new ArrayList<>());
			}
			boolean isPublic = info.getModifiers().stream().anyMatch(mod -> mod == Modifier.PUBLIC);
			String[] types = info.getType().toString().split("\\.");
			outputInfo.get(parentName).add((isPublic ? "+" : "-") + info.getName() + " : " + types[types.length - 1]);			
		}
		return true;
	}
}
