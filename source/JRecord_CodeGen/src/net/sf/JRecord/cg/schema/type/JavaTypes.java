package net.sf.JRecord.cg.schema.type;

public enum JavaTypes implements IJavaFieldType {
		SHORT("short", "int", "IIntField", "ArrayFieldValue.IntArrayField", null, ""),
		INT("int", "IIntField", "ArrayFieldValue.IntArrayField"),
		LONG("long", "ILongField", "ArrayFieldValue.LongArrayField"),
		BIG_INTEGER("BigInteger", "BigInteger", "IBigIntegerField", "ArrayFieldValue.BigIntegerArrayField", 
				null, "java.math.BigInteger"),
		DECIMAL("BigDecimal", "BigDecimal", "IDecimalField", "ArrayFieldValue.DecimalArrayField", null, "java.math.BigDecimal"),
		STRING("String", "IStringField", "ArrayFieldValue.StringArrayField"),
		FLOAT("float", "", ""),
		DOUBLE("double", "", ""),
		OTHER("other", "other",  "FieldValueCG", "ArrayFieldValue.GeneralArrayField", "net.sf.JRecord.cgen.impl.fields.FieldValueCG", ""),
	;
	public final String javaTypeName, javaTypeNameAlt, fieldClass, arrayClass, fieldClassImport, javaTypeImport;

	private JavaTypes(String javaTypeName, String fieldClass, String arrayClass) {
		this(javaTypeName, javaTypeName, fieldClass, arrayClass, null, "");
	}
	
	private JavaTypes(String javaTypeName, String javaTypeNameAlt, String fieldClass, String arrayClass, 
			String fieldClassImport, String javaTypeImport) {
		this.javaTypeName = javaTypeName;
		this.javaTypeNameAlt = javaTypeNameAlt;
		this.fieldClass = fieldClass;
		this.arrayClass = arrayClass;
		this.fieldClassImport = fieldClassImport == null 
				? "net.sf.JRecord.Details.fieldValue." + fieldClass
				: fieldClassImport;
		this.javaTypeImport = javaTypeImport;
	}
	
	public String getFieldClassImport() {
		return "net.sf.JRecord.Details.fieldValue." + fieldClass;
	}
	
	
	public String getArrayClassImport() {
		return "net.sf.JRecord.cgen.impl.array.ArrayFieldValue";
	}

	public String getJavaTypeName() {
		return javaTypeName;
	}

	public String getJavaTypeNameAlt() {
		return javaTypeNameAlt;
	}

	public String getFieldClass() {
		return fieldClass;
	}

	public String getArrayClass() {
		return arrayClass;
	}

	@Override
	public String getJavaTypeImport() {
		return javaTypeImport;
	}

}
