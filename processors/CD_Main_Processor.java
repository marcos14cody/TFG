package processors;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CD_Main_Processor extends FinalProcessor {
	@Override
	public boolean process() {
		try {
			File f = new File("class_diagram.dot");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write("digraph ClassDiagram {\n\tnode [shape = \"record\"]\n");			
			for (String className : CD_Class_Processor.classNames) {
				bw.write("\t\"" + className + "\" [label = \"{" + className + "|" + String.join("\\n", CD_Parameter_Processor.outputInfo.get(className)) + "}\"];\n");
			}
			for (ConnectionInfo connection : CD_OneToMany_Processor.connections) {
				bw.write("\t\"" + connection.getTo() + "\" -> \"" + connection.getFrom() + "\"");
				if (connection.getConnectionName() != null) {
					bw.write(" [ label = \"" + connection.getConnectionName() + "\" ]");
				}
				bw.write(";\n");
			}
			bw.write("}\n");
			bw.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
