package net.sf.JRecord.cg.walker.jrecord;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;

/**
 * This is the call back interface for the {@link SchemaWalker} class.
 * The <ul>
 * <li>startSchema/endSchema methods at the start/end of processing
 * <li>startRecord/endRecord are called at start/end of each record
 * <li>processField is called for every field.
 * </ul
 * 
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
public interface ISchemaWalkerObserver {

	/**
	 * Start processing Cobol-Schema-Definition
	 * @param schema schema (layout) being processed
	 */
	void startSchema(LayoutDef schema);

	/**
	 * Cobol-Schema-Definition has been processed, 
	 * any finalisation should be done now
	 * 
	 * @param schema schema (layout) being processed
	 */
	void endSchema(LayoutDef schema);
	
	/**
	 * Start processing Cobol-Record-Definition
	 * @param record Cobol record to be processed
	 */
	void startRecord(LayoutDef schema, RecordDef record);
	
	/**
	 * Cobol-Record-Definition has been processed, 
	 * any record finalisation should be done now
	 * 
	 * @param record Cobol record to be processed
	 */
	void endRecord(LayoutDef schema, RecordDef record);
	
	/**
	 * process Cobol Field
	 * @param fieldDtls Cobol Field Definition
	 */
	void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls);
	
}
