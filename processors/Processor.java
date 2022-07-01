package processors;

import java.util.List;

import annotations.AnnotationInfo;

public abstract class Processor {
	public abstract boolean process(List<AnnotationInfo> annotations);
}