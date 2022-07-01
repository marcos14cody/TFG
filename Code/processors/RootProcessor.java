package processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import annotations.AnnotationInfo;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RootProcessor extends AbstractProcessor {
	private Map<String, List<AnnotationInfo>> data = new HashMap<String, List<AnnotationInfo>>();
	
	@Override
	public SourceVersion getSupportedSourceVersion() {
		// Always return that the processor supports the latest supported version of the compiler
	    return SourceVersion.latestSupported();
	}
	
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	// If error in enviroment, return error
    	if (roundEnv.errorRaised()) {
			return false;
		}
    	// If processing is over, run data processing and return what the processor returns
    	if (roundEnv.processingOver()) {
    		return processData();
    	}
    	// If an annotation is being processed, for each annotation
        for (TypeElement annotation : annotations) {
        	// Retrieve name
        	String name = annotation.getSimpleName().toString();
        	// Initialize annotation data if necessary
        	if (!data.containsKey(name) ) {
        		data.put(name, new ArrayList<AnnotationInfo>());
        	}
        	// Parse information and add it to data structure
        	data.get(name).addAll(getInfo(annotation, roundEnv));
        }
        return true;
    }
    
    @SuppressWarnings( "deprecation" )
    public boolean processData() {
    	// List of currently processed annotations
    	List<String> processedAnnotations = new ArrayList<>();
    	// Go through all annotations, in order following the dependency graph
    	while (processedAnnotations.size() < data.keySet().size()) {
    		// Find next annotation to process
    		String nextAnn = data.keySet().stream().filter(a -> !processedAnnotations.contains(a) && (!Configuration.DependencyTree.containsKey(a) || Configuration.DependencyTree.get(a).stream().allMatch(ad -> processedAnnotations.contains(ad)))).findFirst().orElse(null);
    		if (nextAnn == null) {
    			System.out.println("ERROR - El grafo de dependencias es circular o contiene errores");
    			return false;
    		}
    		// Get corresponding processor and run it
    		try {
				boolean status = Configuration.Processors.get(nextAnn).newInstance().process(data.get(nextAnn));
				if (!status) {
					System.out.println("ERROR - El procesador de " + nextAnn + " ha lanzado un error");
	    			return false;
				}
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
    		processedAnnotations.add(nextAnn);
    	}
    	// Run final processor
    	try {
			boolean status = Configuration.MainProcessor.newInstance().process();
			if (!status) {
				System.out.println("ERROR - El procesador final ha lanzado un error");
    			return false;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    // Helper function that turns RoundEnviroment information into the more user-friendly custom class of AnnotationInfo
    private List<AnnotationInfo> getInfo(TypeElement annotation, RoundEnvironment roundEnv) {
    	return roundEnv.getElementsAnnotatedWith(annotation)
			.stream()
			.map(e -> {
				return new AnnotationInfo(
					e.getSimpleName().toString(),
					e.getKind(),
					e.asType(),
					e.getModifiers(),
					e.getEnclosingElement().asType().toString(),
					e.getAnnotationMirrors().stream()
						.filter(am -> am.getAnnotationType().toString().equals("annotations." + annotation.getSimpleName().toString()))
						.map(am -> {
							Map<String, AnnotationValue> map = new HashMap<String, AnnotationValue>();
							for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet()) {
								map.put(entry.getKey().getSimpleName().toString(), entry.getValue());
							}
							return map;
						})
						.collect(Collectors.toList())
				);
			})
			.collect(Collectors.toList());
    }

}