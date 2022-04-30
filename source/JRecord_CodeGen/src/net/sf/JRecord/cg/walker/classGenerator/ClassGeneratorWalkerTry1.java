package net.sf.JRecord.cg.walker.classGenerator;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.schema.type.IJavaFieldType;
import net.sf.JRecord.cg.walker.jrecord.IUpdateGenerateParamters;
import net.sf.JRecord.cg.walker.jrecord.StandardSchemaWalkerAdapter;

public class ClassGeneratorWalkerTry1 extends StandardSchemaWalkerAdapter {

	public static final String FIELD_CLASS = "FieldValueCG";

	private final boolean useTypeInterfaces;
	public ClassGeneratorWalkerTry1(Appendable writer, IUpdateGenerateParamters params, boolean useTypeInterfaces) {
		super(	//writer,
				params .setWriter(writer),
				net.sf.JRecord.cgen.impl.fields.FieldValueCG.class,
				net.sf.JRecord.Details.IGetByteData.class); 
		
		this.useTypeInterfaces = useTypeInterfaces;
//		if (useTypeInterfaces) {
//			super.addAllImports("net.sf.JRecord.Details.fieldValue.IDecimalField",
//					"net.sf.JRecord.Details.fieldValue.IIntField",
//					"net.sf.JRecord.Details.fieldValue.ILongField",
//					"net.sf.JRecord.Details.fieldValue.IStringField");
//		}
		super.addAllImports(
					net.sf.JRecord.ByteIO.AbstractByteReader.class, 
					net.sf.JRecord.ByteIO.AbstractByteWriter.class, 
					net.sf.JRecord.ByteIO.ByteIOProvider.class, 
					net.sf.JRecord.Common.Constants.class, 
					net.sf.JRecord.Common.BasicFileSchema.class, 
					net.sf.JRecord.Common.IBasicFileSchema.class
					);
	}

	@Override
	public void startSchema(LayoutDef schema) {
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
		
		if (useTypeInterfaces) {
			for (IJavaFieldType jt : schema.getTypesUsed()) {
				if (jt.getFieldClassImport().length() > 0) {
					super.addAllImports(jt.getFieldClassImport(), jt.getArrayClassImport());
				}
			}
		}
		super.startSchema(schema);
		
		writeln("\tprivate static IBasicFileSchema createBasicSchema() {\n" +
			"\t\treturn " + schema.getBasicSchemaDefinition() + ");\n" +
			"\t}");
		
		writeln("	public static  AbstractByteReader newReader() {\n" + 
				"		return ByteIOProvider.getInstance().getByteReader(createBasicSchema());\n" + 
				"	}\n" + 
				"	public static  AbstractByteWriter newWriter() {\n" + 
				"		return ByteIOProvider.getInstance().getByteWriter(createBasicSchema());\n" + 
				"	}\n" + 
				"");
	}

	@Override public void startRecord(LayoutDef schema, RecordDef record) {
		
//		int len = 0;
//		for (FieldDef fieldDtls : record.getFields()) {
//			IFieldDetail field = fieldDtls.getFieldDetail();
//			len = Math.max(len, field.getPos() + field.getLen());
//		}
		writeln("\tpublic static class Rec" + record.getExtensionName()
				+ " implements IGetByteData {\n"
				+ "\n\t\tpublic byte[] cobolData = new byte[" + record.getRecord().getLength() + "];\n");
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
	
	@Override public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		super.stdWriteField(FIELD_CLASS, useTypeInterfaces,fieldDtls, true);
	}

}
