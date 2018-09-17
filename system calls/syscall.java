import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class syscall {

	public syscall() {
	}

	public static void main(String[] args) {
		Runtime r = Runtime.getRuntime();

		try {
			System.out.println("Initial System Processes");
			Process p = r.exec("ps");
			System.out.println(p);
			InputStream in = p.getInputStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			InputStreamReader inread = new InputStreamReader(buf);
			BufferedReader bufferedreader = new BufferedReader(inread);

			String line;
			while ((line = bufferedreader.readLine()) != null) {
				System.out.println(line);
			}
			
			try {
				if (p.waitFor() != 0) {
					System.err.println("exit value = " + p.exitValue());
				}
			} catch (InterruptedException e) {
				System.err.println(e);
			} finally {
			
				bufferedreader.close();
				inread.close();
				buf.close();
				in.close();
			}
			
			System.out.println("Forking");
			Process f = r.fork("fork
			
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
