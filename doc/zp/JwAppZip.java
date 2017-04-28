import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

public class JwAppZip {
	private String outPath;
	private HashSet<String> set;
	private String appNames;
	private String projPath;
	private String packDirs;

	public JwAppZip() throws Exception {
		String syspath = JwAppZip.class.getResource("/").getPath();
		System.out.println(syspath);
		Properties props = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(syspath + "/app.props"));
		props.load(in);
		projPath = props.getProperty("project.path");
		appNames = props.getProperty("app.name");
		packDirs = props.getProperty("app.dir");
		outPath = props.getProperty("output.path");
		if (outPath == null || outPath.length() < 1) {
			outPath = syspath;
		}
		in.close();
	}

	public void beginpack() throws Exception {
		String[] apps = this.appNames.split(",");
		String[] dirs = this.packDirs.split(",");
		for (String app : apps) {
			set = new HashSet<String>();
			for (String s : dirs) {
				set.add(app + "/" + s + "/");
			}
			String inputFileName = this.projPath + "/" + app;
			// String zipFileName = outPath + "/" + app + ".zip";
			String zipFileName = getFileName(outPath, app);

			System.out.println(zipFileName);

			File inputFile = new File(inputFileName);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
			zip(out, inputFile, app);
			out.close();

			System.out.println("zip ok");
		}

	}

	private boolean check(HashSet<String> set, String path) {
		for (String p : set) {
			if (path.startsWith(p) || p.startsWith(path)) {
				return true;
			}
		}
		return false;
	}

	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (check(set, base + "/")) {
			if (f.isDirectory()) {
				File[] fl = f.listFiles();
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
				base = base.length() == 0 ? "" : base + "/";
				for (int i = 0; i < fl.length; i++) {
					zip(out, fl[i], base + fl[i].getName());
				}
			} else {
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
				FileInputStream in = new FileInputStream(f);
				int b;
				// System.out.println(base);
				while ((b = in.read()) != -1) {
					out.write(b);
				}
				in.close();
			}
		}
	}

	private String getFileName(String path, String appname) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date());

		String filename = path + "/" + appname + "_" + date + ".zip";
		if (!new File(filename).exists()) {
			return filename;
		}
		for (int i = 2; i < 1000; i++) {
			filename = path + "/" + appname + "_" + date + "_" + i + ".zip";
			if (!new File(filename).exists()) {
				return filename;
			}
		}
		return path + "/" + appname + "_" + date + ".zip";
	}

	public static void main(String[] temp) throws Exception {
		JwAppZip book = new JwAppZip();
		book.beginpack();
	}
}
