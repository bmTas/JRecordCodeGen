package net.sf.JRecord.cg.walker.jrecord;

import java.io.Closeable;
import java.io.IOException;

public class WriterMgr implements IWriterMgr {
	private final Appendable writer;

	public WriterMgr(Appendable writer) {
		super();
		this.writer = writer;
	}
	
	
	@Override
	public final void writeln(String s) {
		write(s + "\n");
	}

	@Override
	public final void write(String s) {
		try {
			writer.append(s);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void close() {
		if (writer instanceof Closeable) {
			try {
				((Closeable) writer).close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
