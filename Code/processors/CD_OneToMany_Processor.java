package processors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationValue;

import annotations.AnnotationInfo;

class ConnectionInfo {
	String from;
	String to;
	String connectionName = null;
	public ConnectionInfo(String from, String to, String connectionName) {
		this.from = from;
		this.to = to;
		if (connectionName != "") {
			this.connectionName = connectionName;			
		}
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getConnectionName() {
		return connectionName;
	}
	
	public String toString() {
		String name = "";
		if (this.connectionName != null) {
			name = "-*" + this.connectionName + "*-";
		}
		return this.from + " -" + name + "-> " + this.to;
	}
}

public class CD_OneToMany_Processor extends Processor {
	public static List<ConnectionInfo> connections = new ArrayList<>();
	@Override
	public boolean process(List<AnnotationInfo> annotations) {
		for (AnnotationInfo info : annotations) {
			Map<String, AnnotationValue> values = info.getValuesFlattened();
			String to = values.get("many").getValue().toString();
			if (!CD_Class_Processor.classNames.contains(to)) {
				System.out.println("ERROR - El parametro 'many' debe ser el nombre de otra de las clases");
				return false;
			}
			String connectionName = "";
			Object connectionNameValue;
			if (values.containsKey("connectionName") && (connectionNameValue = values.get("connectionName").getValue()) != null) {
				connectionName = connectionNameValue.toString();
			}
			connections.add(new ConnectionInfo(info.getParentName(), to, connectionName));
		}
		return true;
	}

}
