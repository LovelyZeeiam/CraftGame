package xueli.craftgame.resource;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

	public InputStream openInputStream() throws IOException;

}
