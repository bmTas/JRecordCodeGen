package net.sf.JRecord.cg.interfaceDtls;

import java.io.IOException;

import net.sf.JRecord.Details.IGetLayout;
import net.sf.JRecord.Details.LayoutDetail;
import net.sf.JRecord.External.ExternalRecord;
import net.sf.JRecord.cg.schema.LayoutDef;
import net.sf.JRecord.cg.walker.SchemaWalkerBuilder;
import net.sf.JRecord.cg.walker.ISchemaWalkerBuilder;
import net.sf.JRecord.cg.walker.ISchemaWalkerInterface;
import net.sf.JRecord.cg.walker.jrecord.SchemaWalker;
import net.sf.JRecord.cg.walker.jrecord.param.GenerateParameters;

/**
 * 
 * @author Bruce Martin
 *
 */
public class CGWalker implements ISchemaWalkerInterface {

	@Override
	public GenerateParameters newClassDetailsParam() { 
		return new GenerateParameters();
	}
	
	
	@Override
	public ISchemaWalkerBuilder newBuilder(ExternalRecord xRecord) throws IOException {
		return new SchemaWalkerBuilder(xRecord.asLayoutDetail());
	}

	@Override
	public ISchemaWalkerBuilder newBuilder(IGetLayout ioBuilder) throws IOException {
		return new SchemaWalkerBuilder(ioBuilder.getLayout());
	}
	
	@Override
	public ISchemaWalkerBuilder newBuilder(LayoutDetail layout) {
		return new SchemaWalkerBuilder(layout);
	}

	@Override
	public ISchemaWalkerBuilder newBuilder(LayoutDef layoutDef) {
		return new SchemaWalkerBuilder(layoutDef);
	}

	@Override
	public SchemaWalker newSchemaWalker(ExternalRecord xRecord) throws IOException {
		return new SchemaWalker(xRecord.asLayoutDetail());
	}


	/**
	 * This will create a SchemaWalker. The SchemaWalker goes through a JRecord Layout (File Schema)
	 * and calls a listner  
	 * @param ioBuilder any ioBuilder
	 * @return
	 * @throws IOException
	 */
	@Override
	public SchemaWalker newSchemaWalker(IGetLayout ioBuilder) throws IOException {
		return new SchemaWalker(ioBuilder.getLayout());
	}

	@Override
	public SchemaWalker newSchemaWalker(LayoutDetail layout) {
		return new SchemaWalker(layout);
	}

	@Override
	public SchemaWalker newSchemaWalker(LayoutDef layout) {
		return new SchemaWalker(layout);
	}
}
