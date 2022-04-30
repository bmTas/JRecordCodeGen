package net.sf.JRecord.cg.walker;

import java.io.IOException;

import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;

/**
 * Interface to Schema-Walker class. There are 2 basic interfaces<ul>
 *   <li>Create a <i>SchemaWalker</i> and supply your own Observer class to generate Code/Report etc
 *   <li>Use the <i>Builder</i> interface and use the Supplied builders
 * </ul> 
 * 
 * <br/><b>Usage:</b>
 * 
 * <pre>
 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
 *	walkers.newBuilder(ioBuilder)
 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
 *			.generate();
 *
 *</pre>
 *	<b>or</b>
 * <pre>
 *
 *	CodeGenInterface.WALKER.newSchemaWalker(ioBuilder)
 *			.walk(new SchemaWalkerAdapter() {
 *
 *				public void startRecord(LayoutDef schema, RecordDef record) {
 *					System.out.println("\n\t" + record.getCobolName());
 *				}
 *
 *				String spaces = "                                  ";
 *				public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
 *					Usage usage = fieldDtls.getCobolItem().getUsage();
 *					System.out.println("\t\t" 
 *							+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
 *							+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
 *							+ "\t" + fieldDtls.getCobolItem().getPicture()
 *							+ "\t" + (usage == Usage.NONE ? "" : usage)
 *							);
 *				}
 *			});
 *
 *</pre>
 * @author Bruce Martin
 *
 */
public interface ISchemaWalkerInterface {

	/**
	 * Create a new <i>SchemaWalker</i>. A <i>SchemaWalker</i> will walk through a File-Schema
	 * and call an observer class at the Start/End of Schema/Record and for each field
	 * 
	 *	<pre>	
	 *		CodeGenInterface.WALKER.newSchemaWalker(ioBuilder)
	 *			.walk(new SchemaWalkerAdapter() {
	 *
	 *				public void startRecord(LayoutDef schema, RecordDef record) {
	 *					System.out.println("\n\t" + record.getCobolName());
	 *				}
	 *
	 *				String spaces = "                                  ";
	 *				public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	 *					Usage usage = fieldDtls.getCobolItem().getUsage();
	 *					System.out.println("\t\t" 
	 *							+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
	 *							+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
	 *							+ "\t" + fieldDtls.getCobolItem().getPicture()
	 *							+ "\t" + (usage == Usage.NONE ? "" : usage)
	 *							);
	 *				}
	 *			});
	 * </pre>
	 * @param ioBuilder IoBuilder or any class that implements the IGetLayout interface
	 * @return Schema walker to use
	 * @throws IOException
	 */
	SchemaWalker newSchemaWalker(IGetLayout ioBuilder) throws IOException ;
	
	/**
	 * Create a new <i>SchemaWalker</i>. A <i>SchemaWalker</i> will walk through a File-Schema
	 * and call an observer class at the Start/End of Schema/Record and for each field
	 * 
	 *	<pre>	
	 *		CodeGenInterface.WALKER.newSchemaWalker(layout)
	 *			.walk(new SchemaWalkerAdapter() {
	 *
	 *				public void startRecord(LayoutDef schema, RecordDef record) {
	 *					System.out.println("\n\t" + record.getCobolName());
	 *				}
	 *
	 *				String spaces = "                                  ";
	 *				public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	 *					Usage usage = fieldDtls.getCobolItem().getUsage();
	 *					System.out.println("\t\t" 
	 *							+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
	 *							+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
	 *							+ "\t" + fieldDtls.getCobolItem().getPicture()
	 *							+ "\t" + (usage == Usage.NONE ? "" : usage)
	 *							);
	 *				}
	 *			});
	 * </pre>
	 * 
	 * @param layout JRecord File-Schema (or file Attributes)
	 * @return SchemaWalker
	 */
	SchemaWalker newSchemaWalker(LayoutDetail layout);
	
	/**
	 * Create a new <i>SchemaWalker</i>. A <i>SchemaWalker</i> will walk through a File-Schema
	 * and call an observer class at the Start/End of Schema/Record and for each field
	 * 
	 *	<pre>	
	 *		CodeGenInterface.WALKER.newSchemaWalker(layout)
	 *			.walk(new SchemaWalkerAdapter() {
	 *
	 *				public void startRecord(LayoutDef schema, RecordDef record) {
	 *					System.out.println("\n\t" + record.getCobolName());
	 *				}
	 *
	 *				String spaces = "                                  ";
	 *				public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	 *					Usage usage = fieldDtls.getCobolItem().getUsage();
	 *					System.out.println("\t\t" 
	 *							+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
	 *							+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
	 *							+ "\t" + fieldDtls.getCobolItem().getPicture()
	 *							+ "\t" + (usage == Usage.NONE ? "" : usage)
	 *							);
	 *				}
	 *			});
	 * </pre>
	 * 
	 * @param layout CodeGen File-Schema (or file Attributes)
	 * @return SchemaWalker the schema walker 
	 */	
	SchemaWalker newSchemaWalker(LayoutDef layout);
	
	
	/**
	 * Create a new <i>SchemaWalker</i>. A <i>SchemaWalker</i> will walk through a File-Schema
	 * and call an observer class at the Start/End of Schema/Record and for each field
	 * 
	 *	<pre>	
	 *		CodeGenInterface.WALKER.newSchemaWalker(xRecord)
	 *			.walk(new SchemaWalkerAdapter() {
	 *
	 *				public void startRecord(LayoutDef schema, RecordDef record) {
	 *					System.out.println("\n\t" + record.getCobolName());
	 *				}
	 *
	 *				String spaces = "                                  ";
	 *				public void processField(LayoutDef schema, RecordDef record, FieldDef fieldDtls) {
	 *					Usage usage = fieldDtls.getCobolItem().getUsage();
	 *					System.out.println("\t\t" 
	 *							+ (fieldDtls.getCobolName() + spaces).subSequence(0, 32)
	 *							+ fieldDtls.getPos() + "\t" + fieldDtls.getLen() + "\t" + fieldDtls.getDecimal()
	 *							+ "\t" + fieldDtls.getCobolItem().getPicture()
	 *							+ "\t" + (usage == Usage.NONE ? "" : usage)
	 *							);
	 *				}
	 *			});
	 * </pre>
	 * 
	 * @param xRecord JRecord-External-Record
	 * 
	 * @return SchemaWalker
	 */	
	SchemaWalker newSchemaWalker(ExternalRecord xRecord) throws IOException;
	
	/**
	 * Create a Class-Details parameter (Package-Name and Class-Name of the class to be generated).
	 * @return
	 */
	GenerateParameters newClassDetailsParam();

	/**
	 * Purpose:  to create a JRecord-Schema-Walker-Builder
	 * 
	 * <br/><br/>Usage:
	 * 
	 * <pre>
	 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
	 *	walkers.newBuilder(ioBuilder)
	 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
	 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
	 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *			.generate();
	 *</pre>
	 *
	 * @param ioBuilder any class that you can retrieve JRecord-Schema from. This includes IoBuilders
	 * @return Schema-Walker-Builder
	 * @throws IOException
	 */
	ISchemaWalkerBuilder newBuilder(IGetLayout ioBuilder) throws IOException ;
	
	/**
	 * Purpose:  to create a JRecord-Schema-Walker-Builder
	 * 
	 * Usage:
	 * 
	 * <pre>
	 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
	 *	walkers.newBuilder(ioBuilder)
	 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
	 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
	 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *			.generate();
	 *</pre>
	 * 
	 * @param layout Layout (File Schema) to create a JRecord-Schema-Walker-Builder from.
	 * @return  Schema-Walker-Builder
	 */
	ISchemaWalkerBuilder newBuilder(LayoutDetail layout);
	
	/**
	 * Purpose:  to create a JRecord-Schema-Walker-Builder
	 * 
	 * Usage:
	 * 
	 * <pre>
	 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
	 *	walkers.newBuilder(ioBuilder)
	 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
	 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
	 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *			.generate();
	 *</pre>
	 * 
	 * @param layout Layout (File Schema) to create a JRecord-Schema-Walker-Builder from.
	 * @return  Schema-Walker-Builder
	 */
	ISchemaWalkerBuilder newBuilder(LayoutDef layout);
	
	
	/**
	 * Purpose:  to create a JRecord-Schema-Walker-Builder
	 * 
	 * Usage:
	 * 
	 * <pre>
	 *	ICobolWalkerInterface walker = CodeGenInterface.WALKER;
	 *	walkers.newBuilder(xRecord)
	 *				.setOutputDirectory("/home/bruce/work/JRecord/CodeGen")
	 *				.setCobolDataClassPackageName("test.walker.Builder.GenerateCode.data")
	 *				.setReadClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *				.setWriteClassGenerateParameters(walker.newClassDetailsParam()
	 *													.setPackageName("test.walker.Builder.GenerateCode.example"))
	 *			.generate();
	 *</pre>
	 * 
	 * @param layout Layout (File Schema) to create a JRecord-Schema-Walker-Builder from.
	 * @return  Schema-Walker-Builder
	 */
	ISchemaWalkerBuilder newBuilder(ExternalRecord xRecord) throws IOException;
}
