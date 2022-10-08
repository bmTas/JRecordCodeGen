package net.sf.JRecord.cg.schema.type;

/**
 * Define The java type details for a Cobol field
 * 
 * @author Bruce Martin
 *
 */
public interface IJavaFieldType {
	/**
	 * Java type e.g. int, long, String BigDecimal
	 * @return type name as String
	 */
	public String getJavaTypeName();
	/**
	 * Java type to use as an alternative e.g. short is converted to int
	 * @return alternative type name as String
	 */
	public String getJavaTypeNameAlt();
	
	/** 
	 * JRecord Class/interface short name (no package name)
	 *  to use for this type.
	 * @return JRecord Class/interface to use
	 */
	public String getFieldClass();
	/**
	 * JRecord Array class/interface short name (no package name)
	 * to use for this type
	 * @return JRecord Array class
	 */
	public String getArrayClass();
	/** 
	 * JRecord Class/interface full name (including package name)
	 * to use for this type. This is what you use in an import statement
	 * @return JRecord Class/interface to use
	 */
	public String getFieldClassImport();
	/**
	 * JRecord Array class/interface full name (including package name)
	 * to use for this type. This is what you use in an import statement
	 * @return JRecord Array class
	 */
	public String getArrayClassImport();
	
	/**
	 * import for java type e.g. java.math.BigDecimal 
	 * @return what to use in an import statement.  
	 */
	public String getJavaTypeImport();
}