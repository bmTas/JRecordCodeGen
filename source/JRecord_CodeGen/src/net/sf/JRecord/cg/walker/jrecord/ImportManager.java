package net.sf.JRecord.cg.walker.jrecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ImportManager {
	private final List<String> imports;

	public ImportManager(List<String> imports) {
		super();
		this.imports = new ArrayList<String>(imports);
	}
	public ImportManager() {
		super();
		this.imports = new ArrayList<String>();
	}
	
	public String generateImports() {
		StringBuilder b = new StringBuilder();
		
		String lastImport= "";
		imports.sort(Comparator.naturalOrder());
		for (String importName : imports) {
			if (importName != null && importName.length() > 0 && ! lastImport.equals(importName)) {
				b.append("import ").append(importName + ";\n");
				lastImport = importName;
			}
		}
		return b.toString();
	}
	

	public void addImport(String arg0) {
		imports.add(arg0);
	}
	
	public void addAllImports(String...  importClasses ) {
		for (String s : importClasses) {
			this.imports.add(s);		
		}
	}
	
	public void addImport(Class<?> c) {
		this.imports.add(c.getCanonicalName());
	}

	public void addAllImports(Class<?>... classes) {
		for (Class<?> c : classes) {
			this.imports.add(c.getCanonicalName());
		}
	}


	public void addAllImports(Collection<? extends String> arg0) {
		imports.addAll(arg0);
	}

}
