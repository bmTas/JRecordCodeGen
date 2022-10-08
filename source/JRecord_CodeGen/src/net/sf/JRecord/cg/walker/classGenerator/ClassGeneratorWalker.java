package net.sf.JRecord.cg.walker.classGenerator;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.schema.type.IJavaFieldType;
import net.sf.JRecord.cg.walker.jrecord.IUpdateGenerateParamters;
import net.sf.JRecord.cg.walker.jrecord.StandardSchemaWalkerAdapter;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;

public class ClassGeneratorWalker extends StandardSchemaWalkerAdapter {

	public static final String FIELD_CLASS = "FieldValueCG";

	//private final boolean useTypeInterfaces;
	private boolean multiRecord;
	
	public ClassGeneratorWalker(Appendable writer, String packageId) {
		this( new GenerateParameters()
				.setWriter(writer)
				.setPackageName(packageId));
	}
	
//	public ClassGeneratorWalker(Appendable writer, IUpdateGenerateParamters parameters) {
//		this(new WriterMgr(writer), parameters);
//	}
	
	public ClassGeneratorWalker(IUpdateGenerateParamters parameters) {
		super(	parameters,
				//"java.io.IOException",
				//"java.io.InputStream",
				//"java.io.OutputStream",
				net.sf.JRecord.cgen.impl.fields.FieldValueCG.class,
				net.sf.JRecord.cgen.def.IDeserializer.class,
//				"net.sf.JRecord.cgen.def.IReader",
//				"net.sf.JRecord.cgen.def.IWriter",
//				"net.sf.JRecord.cgen.impl.io.ReadFromBytes",
//				"net.sf.JRecord.cgen.impl.io.WriteAsBytes",
				net.sf.JRecord.Common.Constants.class, 
				net.sf.JRecord.Common.BasicFileSchema.class, 
//				"net.sf.JRecord.Common.IBasicFileSchema",
				net.sf.JRecord.Details.IGetByteData.class,
				net.sf.JRecord.cgen.impl.io.IoProvider.class
				);

		
//		super.addAllImports(
//					"net.sf.JRecord.Common.Constants", 
//					"net.sf.JRecord.Common.BasicFileSchema", 
//					"net.sf.JRecord.Common.IBasicFileSchema",
//					"net.sf.JRecord.cgen.def.IDeserializer",
//					"net.sf.JRecord.cgen.def.IReader",
//					"net.sf.JRecord.cgen.def.IWriter",
//					"net.sf.JRecord.cgen.impl.io.ReadFromBytes",
//					"net.sf.JRecord.cgen.impl.io.WriteAsBytes"
//					);
	}


	
	@Override
	public void startSchema(LayoutDef schema) {
		
		params.setClassName(getClassName(schema));
		
		String className = params.getClassName();

		multiRecord = schema.getRecords().size() > 1;
		if (schema.isArrayPresent()) {
			super.addAllImports(
					net.sf.JRecord.cgen.def.array.IArrayField.class,
					net.sf.JRecord.cgen.impl.array.ArrayFieldValue.class,
					net.sf.JRecord.cgen.support.ArrayGenField.class,
					net.sf.JRecord.cgen.def.array.IArrayField1Dimension.class,
					net.sf.JRecord.cgen.def.array.IArrayField2Dimension.class,
					net.sf.JRecord.cgen.def.array.IArrayField3Dimension.class,
					net.sf.JRecord.cgen.def.array.IArrayFieldGeneric.class
					);
		}
		
		for (IJavaFieldType jt : schema.getTypesUsed()) {
			if (jt.getFieldClassImport().length() > 0) {
				super.addAllImports(jt.getFieldClassImport(), jt.getArrayClassImport());
			}
		}
		
		super.writeClassHeader();
		super.writeClassStatment(schema, ! multiRecord);
		
		if (multiRecord) {
			String parentInterface = getParentInterface();
			writeIoProvider(schema, className, parentInterface);

			for (RecordDef rec : schema.getRecords()) {
				writeln("\tpublic final Rec" + rec.getExtensionName() + " " + rec.getJavaName() +";"); 
			}
			
			writeConstructor(schema, "");
			writeConstructor(schema, "data");
			
			writeln("\n\tpublic static interface " + parentInterface + " extends IGetByteData {}" );
		} else {
			writeIoProvider(schema, className, className);
			writeln("\n\tprivate byte[] cobolData;");
			writeln("\n\tpublic " + super.getClassName() + "() {"
					+ "\n\t\tcobolData = new byte[" + schema.getSchema().getMaximumRecordLength() + "];"
					+ "\n\t}");
			writeln("\n\tpublic " + super.getClassName() + "(byte[] cobolData) {"
				+ "\n\t\tthis.cobolData = cobolData;"
				+ "\n\t}\n");
		}
		
//		writeln("\tprivate static IBasicFileSchema createBasicSchema() {\n" +
//			"\t\treturn " + schema.getBasicSchemaDefinition() + ");\n" +
//			"\t}");
//		
//		String className = params.getClassName();
//		writeln("	public static  IReader<" +  className + "> newReader(InputStream in) throws IOException {\n" + 
//				"		return ReadFromBytes.newReader(createBasicSchema(),"
//						+ " new IDeserializer<"	+  className + ">() {\n" + 
//				"			@Override public " +  className + " deserialize(byte[] rec) {\n" + 
//				"				return new " +  className + "(rec);\n" + 
//				"			}\n" + 
//				"\t\t},\n" 
//				+ "\t\tin);\n" +
//				"	}\n\n"); 
//		writeln("\tpublic static IWriter<IGetByteData> newWriter(OutputStream out) throws IOException {\n" + 
//				"		return WriteAsBytes.newGetBytesWriter(createBasicSchema(), out);\n" + 
//				"\t}\n");
	}
	
	private void writeIoProvider(LayoutDef schema, String readClassName, String writeClassName) {
		String id = "<" + readClassName + ", " + writeClassName + ">";
		
		writeln("\n" +
				"	private static IoProvider" + id + " provider = new IoProvider" + id + "(\n" + 
				"			" + schema.getBasicSchemaDefinition() + ", \n" + 
				"			new IDeserializer<" + readClassName + ">() {\n" + 
				"				@Override public " + readClassName + " deserialize(byte[] rec) { return new " + readClassName + "(rec); }\n" + 
				"			});\n" + 
				"	\n" + 
				"	public static IoProvider" + id + " getIoProvider() { return provider;};\n" + 
				"");
	}

	/**
	 * @param schema
	 * @param dataName
	 */
	protected void writeConstructor(LayoutDef schema, String dataName) {
		String decl = dataName.length() == 0 ? "" : "byte[] " + dataName;
		writeln("\n\n\tpublic " + super.getClassName() + "(" + decl + ") {");
		for (RecordDef rec : schema.getRecords()) {
			writeln("\t\t" + rec.getJavaName() +  " = new Rec" + rec.getExtensionName() + "(" + dataName + ");"); 
		}
		writeln("\n\t}");
	}
	
	private String getParentInterface() {
		return "I" + params.getClassName() + "Children";
 	}

	@Override public void startRecord(LayoutDef schema, RecordDef record) {
		
//		int len = 0;
//		for (FieldDef fieldDtls : record.getFields()) {
//			IFieldDetail field = fieldDtls.getFieldDetail();
//			len = Math.max(len, field.getPos() + field.getLen());
//		}
		if (multiRecord) {
			writeln("\tpublic static class Rec" + record.getExtensionName()
					+ " implements " + getParentInterface() + " {\n");
			writeln( "\n\t\tprivate byte[] cobolData = new byte[" + record.getRecord().getLength() + "];\n"
					+ "\n\n\t\tpublic Rec" + record.getExtensionName() + " (){}"
					+ "\n\n\t\tpublic Rec" + record.getExtensionName() + " (byte[] cobolData){"
					+ "\n\t\t\tthis.cobolData = cobolData;\n\t\t}\n"
					);
		}
		writeln(
				"		@Override public byte[] getData() {\n" + 
				"			return cobolData;\n" + 
				"		}\n" + 
				"\n" + 
				"		@Override public void setData(byte[] data) {\n" + 
				"			this.cobolData = data;\n" + 
				"		}\n" + 
				"");
	}
	
	@Override
	public void endRecord(LayoutDef schema, RecordDef record) {
		if (multiRecord) {
			super.endRecord(schema, record);
		}
	}

	@Override public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		super.stdWriteField(FIELD_CLASS, true,fieldDtls, multiRecord);
	}
}
