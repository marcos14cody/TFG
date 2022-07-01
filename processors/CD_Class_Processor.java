package processors;

import java.util.ArrayList;
import java.util.List;

import annotations.AnnotationInfo;

public class CD_Class_Processor extends Processor {
	public static List<String> classNames = new ArrayList<>();
	
	@Override
	public boolean process(List<AnnotationInfo> infos) {
		for (AnnotationInfo info : infos) {
			classNames.add(info.getType().toString());			
		}
		return true;
	}
}
