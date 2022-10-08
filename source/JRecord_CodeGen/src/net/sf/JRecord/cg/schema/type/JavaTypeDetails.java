package net.sf.JRecord.cg.schema.type;

import java.util.HashMap;

import net.sf.JRecord.Common.FieldDetail;
import net.sf.JRecord.cg.schema.classDefinitions.ClassDef;
import net.sf.JRecord.cg.schema.classDefinitions.IClassDef;
import net.sf.JRecord.cgen.support.Code2JRecordConstants;

public class JavaTypeDetails {
	public final String javaTypeName, javaRawType;
	public final IClassDef classDef;
	public final boolean isInteger;
	public final IJavaFieldType javaTypeInfo;
	
	private static HashMap<String,JavaTypes> nameToJavaType = new HashMap<String,JavaTypes>(20);
	
	static {
		for (JavaTypes jt :JavaTypes.values()) {
			if (jt.fieldClass.length() > 0) {
				nameToJavaType.put(jt.javaTypeName, jt);
			}
		}
	}
	
	public JavaTypeDetails(boolean isCsv, FieldDetail fieldDef) {
		this(isCsv, fieldDef.getType(), 
				fieldDef.getJavaTypeIfSet(),
				fieldDef.getLen(), 
				fieldDef.getDecimal(), fieldDef.getFormat(), fieldDef.getParamater());
	}
	
	
	public JavaTypeDetails(boolean isCsv, int type, String typeName, int len, int decimal, int format, String param) {

		this.classDef = ClassDef.getClassDef(format, param);
		this.javaRawType = typeName == null || typeName.length() == 0
				? Code2JRecordConstants.typeToJavaType(isCsv, type, len, decimal)
				: typeName;
		this.javaTypeName = classDef== null || classDef.getClassName() == null ? javaRawType : classDef.getClassName();
		this.isInteger = "short".equals(javaRawType) || "int".equals(javaRawType) || "long".equals(javaRawType);
		
		IJavaFieldType jt = nameToJavaType.get(javaTypeName);
		
		this.javaTypeInfo = jt == null ? JavaTypes.OTHER : jt;
	}
}
