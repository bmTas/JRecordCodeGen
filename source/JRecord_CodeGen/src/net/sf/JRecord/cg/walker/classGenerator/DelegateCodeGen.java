package net.sf.JRecord.cg.walker.classGenerator;

import net.sf.JRecord.cg.schema.FieldDef;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.schema.RecordDef;
import net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver;

/**
 * 
 * @author Bruce Martin
 *
 */
public abstract class DelegateCodeGen {

//	private ISchemaWalkerObserver codeGen;

	public DelegateCodeGen() {
		super();
	}

	/**
	 * @param schema file schema
	 * @see net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver#startSchema(net.sf.JRecord.cg.schema.LayoutDef)
	 */
	public void startSchema(LayoutDef schema) {
		getWalker(schema).startSchema(schema);
	}

	/**
	 * @param schema file schema
	 * @see net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver#endSchema(net.sf.JRecord.cg.schema.LayoutDef)
	 */
	public void endSchema(LayoutDef schema) {
		getWalker(schema).endSchema(schema);
	}

	/**
	 * @param schema file schema
	 * @param record file Record definition
	 * @see net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver#startRecord(net.sf.JRecord.cg.schema.LayoutDef, net.sf.JRecord.cg.schema.RecordDef)
	 */
	public void startRecord(LayoutDef schema, RecordDef record) {
		getWalker(schema).startRecord(schema, record);
	}

	/**
	 * @param schema file schema
	 * @param record file Record definition
	 * @see net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver#endRecord(net.sf.JRecord.cg.schema.LayoutDef, net.sf.JRecord.cg.schema.RecordDef)
	 */
	public void endRecord(LayoutDef schema, RecordDef record) {
		getWalker(schema).endRecord(schema, record);
	}

	/**
	 * @param schema file schema
	 * @param record file Record definition
	 * @param fieldDtls field definition
	 * @see net.sf.JRecord.cg.walker.jrecord.ISchemaWalkerObserver#processField(net.sf.JRecord.cg.schema.LayoutDef, net.sf.JRecord.cg.schema.RecordDef, net.sf.JRecord.cg.schema.FieldDef)
	 */
	public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
		getWalker(schema).processField(schema, record, fieldDtls);
	}

	/**
	 * Create CodeGenWalker to be used as a delegate
	 * @param schema file schema
	 */
	protected abstract ISchemaWalkerObserver getWalker(LayoutDef schema) ;
}