package annotations;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

public class AnnotationInfo {
	private String name;
	private ElementKind kind;
	private TypeMirror type;
	private Set<Modifier> modifiers;
	private String parentName;
	private List<Map<String, AnnotationValue>> values;
	
	public AnnotationInfo(String name, ElementKind kind, TypeMirror type, Set<Modifier> modifiers, String parentName, List<Map<String, AnnotationValue>> values) {
		this.name = name;
		this.kind = kind;
		this.type = type;
		this.modifiers = modifiers;
		this.parentName = parentName;
		this.values = values;
	}
	
	public String getName() {
		return name;
	}
	
	public ElementKind getKind() {
		return kind;
	}
	
	public TypeMirror getType() {
		return type;
	}
	
	public Set<Modifier> getModifiers() {
		return modifiers;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public List<Map<String, AnnotationValue>> getValues() {
		return values;
	}
	
	public String toString() {
		return name + " " + kind + " - " + modifiers + " -- " + values;
	}

	public Map<String, AnnotationValue> getValuesFlattened() {
		Map<String, AnnotationValue> combined = new HashMap<String, AnnotationValue>();
		for (Map<String, AnnotationValue> m : values) {
			for (String s : m.keySet()) {
				combined.put(s, m.get(s));
			}
		}
		return combined;
	}
}
