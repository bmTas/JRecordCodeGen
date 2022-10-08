package net.sf.JRecord.cg.walker.jrecord;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;

/**
 * Basic SchemaWalker listner implementation ({@link ISchemaWalkerObserver}) and {@link SchemaWalker}
 * You should override the methods you choose to use.
 * 
 * <b>JRecord Schema</b> format:
 * <pre>
 *     LayoutDef  - Describes a file (LayoutDetail in JRecord)
 *       |
 *       +----- RecordDef (1 or More) - Describes one record in the file (RecordDetail in JRecord)
 *                |
 *                +------  FieldDef (1 or More)  - Describes one field in the file (FieldDetail in JRecord)
 *                
 *  Example of using the SchemaWalkerAdapter in the walk method
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
public class SchemaWalkerAdapter implements ISchemaWalkerObserver {

	@Override
	public void startSchema(LayoutDef schema) {
	}

	@Override
	public void endSchema(LayoutDef schema) {
	}

	@Override
	public void startRecord(LayoutDef schema, RecordDef record) {
	}

	@Override
	public void endRecord(LayoutDef schema, RecordDef record) {
	}

	@Override
	public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	}

}
