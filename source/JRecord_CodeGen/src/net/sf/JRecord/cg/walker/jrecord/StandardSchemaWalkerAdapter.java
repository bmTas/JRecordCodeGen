package net.sf.JRecord.cg.walker.jrecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;


/**
 * This adapter will generate a Java class for a Cobol Copybook. Each Record in the 
 * copybook will have its own embedded static class and method to create it.
 * It is used by the {@link SchemaWalker} class and implements ({@link ISchemaWalkerObserver}) 
 * 
 * You need to write a processField method. Override the other methods as needed.
 * 
 * <b>JRecord Schema</b> format:
 * <pre>
 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
 *       |
 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
 *                |
 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
 * </pre>
 * 
 * @author Bruce Martin
 *
 */
public abstract class StandardSchemaWalkerAdapter implements ISchemaWalkerObserver {

	private static final List<String> EMPTY_LIST = Arrays.asList();
	
//	private final String packageId;
	//private final List<String> imports;
	final ImportManager importMgr;
	
	private String className = null;
//	private final Appendable writer;
	
	private IWriterMgr writerMgr;
	protected final IUpdateGenerateParamters params;
	
	
	
	
	
	public StandardSchemaWalkerAdapter(Appendable writer) {
		this(new GenerateParameters()	.setWriter(writer), EMPTY_LIST);
	}

	public StandardSchemaWalkerAdapter( IUpdateGenerateParamters params, String... javaImport) {
		this(params, javaImport== null ? null : Arrays.asList(javaImport));
	}

	public StandardSchemaWalkerAdapter( IUpdateGenerateParamters params, Class<?>... javaImport) {
		this(params, (List<String>) null);
		this.importMgr.addAllImports(javaImport);
	}

	public StandardSchemaWalkerAdapter( IUpdateGenerateParamters params, List<String> imports) {
		super();
		
		//this.writerMgr = params.getWriter();
		this.params =  params;
		this.importMgr = new ImportManager(imports== null ? EMPTY_LIST : imports);
		

		className = params.getClassName();
	}

	@Override
	public void startSchema(LayoutDef schema) {
		
		writeClassHeader();
		writeClassStatment(schema, false);
	
		for (RecordDef rec : schema.getRecords()) {
			writeln("\tpublic static Rec" + rec.getExtensionName() + " new" + rec.getExtensionName() 
					+ "() { return new Rec" + rec.getExtensionName() + "();}\n");
		}
	}

	/**
	 *  Write The class header
	 */
	protected void writeClassHeader() {
		String packageId = params.getPackageName();
		if (packageId != null && packageId.length() > 0) {
			writeln("package " + packageId + ";\n");
		}
		addAllImports(
				net.sf.JRecord.Common.FieldDetail.class,
				net.sf.JRecord.Types.Type.class ); 

		writeln(importMgr.generateImports());
	}


	/**
	 * @param schema
	 */
	protected void writeClassStatment(LayoutDef schema, boolean implementGetData) {
		String implementsStr = implementGetData ? "implements IGetByteData " : "";
		
		writeln("\npublic class " + getClassName(schema) + " " + implementsStr + "{\n");
	}

	/**
	 * @param schema
	 */
	protected String getClassName(LayoutDef schema) {
		if (className == null || className.length() == 0) {
			className = schema.getExtensionName();
			params.setClassName(className);
		}
		return className;
	}

	@Override
	public void endSchema(LayoutDef schema) {
		getWriterMgr().writeln("}");
		writerMgr.close();
	}
	

	@Override
	public void startRecord(LayoutDef schema, RecordDef record) {
		writeln("\tpublic static class Rec" + record.getExtensionName() + " {");
	}

	@Override
	public void endRecord(LayoutDef schema, RecordDef record) {
		writeln("\t}\n");
	}

	public String getClassName() {
		return className;
	}

//	public StandardSchemaWalkerAdapter setClassName(String className) {
//		this.className = className;
//		
//		return this;
//	}
	
	/**
	 * @param fieldDtls
	 */
	protected void stdWriteField(String fieldClassName, boolean useTypeInterfaces, FieldDef fieldDtls, boolean extraIndent) {
		String fieldClass = fieldClassName;
		String arrayClass = "ArrayFieldValue.GeneralArrayField";
		String indent = extraIndent ? "\t\t" : "\t";
		
		if (useTypeInterfaces && fieldDtls.javaTypeInfo != null) {
			if (fieldDtls.javaTypeInfo.getFieldClass().length() > 0) {
				fieldClass = fieldDtls.javaTypeInfo.getFieldClass();
			}
			if (fieldDtls.javaTypeInfo.getArrayClass().length() > 0) {
				arrayClass = fieldDtls.javaTypeInfo.getArrayClass();
			}
		}
		
		if (fieldDtls.isArrayItem()) {
			if (fieldDtls.getArrayIndexDetails().isFirstIndex()) {
				String interfaceName = "IArrayField";
				switch (fieldDtls.getArrayDefinition().getDimensionCount()) {
				case 1:
				case 2:
				case 3:
					interfaceName = "IArrayField" + fieldDtls.getArrayDefinition().getDimensionString() + "Dimension";
				}
//				writeln("\t\tpublic final IArrayField<" + fieldClass + "> " + fieldDtls.getJavaName()
				writeln(indent + "public final " + interfaceName + "<" + fieldClass + "> " + fieldDtls.getArrayIndexDetails().getJavaName()
				+ " = new " + arrayClass + "(" + fieldDtls.getJRecordArrayDefinition(fieldClass)
				+ ", this);");
//				writeln(
//						"\t\tpublic final IArrayField<" + fieldClass + "> " + fieldDtls.getJavaName()
//						+ " = new " + arrayClass 
//						+ "("
//						+ "this, "
//						+ fieldDtls.getJRecordFieldDefinition() + ");");
			}
		} else {
			writeln(indent
					+ "public final " + fieldClass + " " + fieldDtls.getJavaName()
					+ " = new " + fieldClassName 
					+ "("
					+ "this, "
					+ fieldDtls.getJRecordFieldDefinition() + ");");
		}
	}


//	public StandardSchemaWalkerAdapter addImport(String arg0) {
//		checkImports();
//		imports.add(arg0);
//		
//		return this;
//	}
	
	public StandardSchemaWalkerAdapter addAllImports(Class<?>...  importClasses ) {
		importMgr.addAllImports(importClasses);
		
		return this;
	}

	public StandardSchemaWalkerAdapter addAllImports(String...  importClasses ) {
		importMgr.addAllImports(importClasses);
		
		return this;
	}

	public StandardSchemaWalkerAdapter addAllImports(Collection<? extends String> arg0) {
		importMgr.addAllImports(arg0);
		
		return this;
	}
	
	protected final void writeln(String s) {
		getWriterMgr().writeln(s);
	}

	protected final void write(String s) {
		getWriterMgr().write(s);
	}
	
	private IWriterMgr getWriterMgr() {
		if (writerMgr == null) {
			writerMgr = params.getWriter();
		}
		return writerMgr;
	}
}
