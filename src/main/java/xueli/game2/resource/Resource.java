package xueli.game2.resource;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

	public String getName();
	public InputStream openInputStream() throws IOException;

}
