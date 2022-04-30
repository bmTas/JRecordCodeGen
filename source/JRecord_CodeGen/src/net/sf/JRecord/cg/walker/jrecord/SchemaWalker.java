package net.sf.JRecord.cg.walker.jrecord;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.walker.classGenerator.SingleRecordReaderCodeGen;
import net.sf.JRecord.cg.walker.classGenerator.SingleRecordWriterCodeGen;
import net.sf.JRecord.cgen.def.ILayoutDetails4gen;

/**
 * This class walks through JRecord Schema and class a supplied listener (ISchemaWalkerListner) at each
 * level in the schema :
 * 
 * <pre>
 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
 *       |
 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
 *                |
 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
 *                
 *  Example of using the walk method
 *  
 *	CodeGenInterface.WALKER.newSchemaWalker(ioBuilder)
 *		.walk(new SchemaWalkerAdapter() {
 *
 *			public void startRecord(LayoutDef schema, RecordDef record) {
 *				System.out.println("\n\t" + record.getCobolName());
 *			}
 *
 *			String spaces = "                                  ";
 *			public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
 *				Usage usage = fieldDtls.getCobolItem().getUsage();
 *				System.out.println("\t\t" 
 *						+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
 *						+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
 *						+ "\t" + fieldDtls.getCobolItem().getPicture()
 *						+ "\t" + (usage == Usage.NONE ? "" : usage)
 *						);
 *			}
 *		});
 * </pre>
 * 
 * @author Bruce Martin
 *
 */
public class SchemaWalker {
	private final LayoutDef xSchema;
	
	/**
	 *  * This class walks through JRecord Schema and class a supplied listener (ISchemaWalkerListner) at each
	 * level in the schema :
	 * 
	 * <pre>
	 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
	 *       |
	 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
	 *                |
	 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
	 * </pre>
	 * 
	 * @param schema to be processed
	 */
	public SchemaWalker(ILayoutDetails4gen schema) {
		xSchema = new LayoutDef(schema, schema.getLayoutName(), null);
	}


	/**
	 *  * This class walks through JRecord Schema and class a supplied listener (ISchemaWalkerListner) at each
	 * level in the schema :
	 * 
	 * <pre>
	 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
	 *       |
	 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
	 *                |
	 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
	 * </pre>
	 * 
	 * @param schema to be processed
	 */
	public SchemaWalker(LayoutDef schema) {
		xSchema = schema;
	}

	/**
	 * This class walks through a JRecord Schema and calls a user supplied
	 * 
	 * <pre>
	 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
	 *       |
	 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
	 *                |
	 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
	 * 
	 *  Example of using the walk method
	 *  
	 *	CodeGenInterface.WALKER.newSchemaWalker(ioBuilder)
	 *		.walk(new SchemaWalkerAdapter() {
	 *
	 *			public void startRecord(LayoutDef schema, RecordDef record) {
	 *				System.out.println("\n\t" + record.getCobolName());
	 *			}
	 *
	 *			String spaces = "                                  ";
	 *			public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	 *				Usage usage = fieldDtls.getCobolItem().getUsage();
	 *				System.out.println("\t\t" 
	 *						+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
	 *						+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
	 *						+ "\t" + fieldDtls.getCobolItem().getPicture()
	 *						+ "\t" + (usage == Usage.NONE ? "" : usage)
	 *						);
	 *			}
	 *		});
	 * </pre>
	 * 
	 * There are other examples in {@link SingleRecordReaderCodeGen}, {@link SingleRecordWriterCodeGen},
	 * and {link ClassGeneratorWalker} + others.
	 * 
	 * @param listner Schema-Listener. You can use start with either<ul>
	 * <li> {@link SchemaWalkerAdapter} a basic implementation of <i>ISchemaWalkerListener</i> with empty methods
	 * <li> {@link StandardSchemaWalkerAdapter} implementation of <i>ISchemaWalkerListener</i> that
	 * produces basic class for the schema and child classes for the records.
	 * </ul>
	 * 
	 */
	public SchemaWalker walk(ISchemaWalkerObserver listner) {
		
		listner.startSchema(xSchema);
		for (RecordDef rec : xSchema.getRecords()) {
			listner.startRecord(xSchema, rec);
			for (FieldDef fieldDtls : rec.getFields()) {
				listner.processField(xSchema, rec, fieldDtls);
			}
			listner.endRecord(xSchema, rec);
		}
		listner.endSchema(xSchema);
		
		return this;
	}
}
