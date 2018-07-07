package de.janst.trajectory.config;

import java.io.IOException;

public class PluginConfiguration extends Configuration {

	public PluginConfiguration() throws IOException {
		super("config.yml", true);
	}
	
	public int getTickSpeed() {
		return config.getInt("ticks-between-particles");
	}
	
	public boolean saveInstant() {
		return config.getBoolean("save-instant");
	}

	public int getSaveInterval() {
		return config.getInt("save-interval");
	}
	
	public int getMaximalLength() {
		return config.getInt("maximal-length");
	}
	
	public boolean allowParticleChange() {
		return config.getBoolean("allow-particle-change");
	}
	
	public boolean isUpToDate(String actualVersion) {
		String version = config.getString("config-version");
		if(version == null)
			return false;
		if(versionCompare(actualVersion, version) == 1)
			return false;
		else
			return true;
	}
	
	/**
	 * Compares two version strings.  src: http://stackoverflow.com/questions/6701948/efficient-way-to-compare-version-strings-in-java
	 * 
	 * Use this instead of String.compareTo() for a non-lexicographical 
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 * 
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 * 
	 * @param str1 a string of ordinal numbers separated by decimal points. 
	 * @param str2 a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than str2. 
	 *         The result is a positive integer if str1 is _numerically_ greater than str2. 
	 * http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1789445        The result is zero if the strings are _numerically_ equal.
	 */
	public Integer versionCompare(String str1, String str2) {
	    String[] vals1 = str1.split("\\.");
	    String[] vals2 = str2.split("\\.");
	    int i = 0;
	    // set index to first non-equal ordinal or length of shortest version string
	    while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) 
	    {
	      i++;
	    }
	    // compare first non-equal ordinal number
	    if (i < vals1.length && i < vals2.length) 
	    {
	        int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
	        return Integer.signum(diff);
	    }
	    // the strings are equal or one string is a substring of the other
	    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
	    else
	    {
	        return Integer.signum(vals1.length - vals2.length);
	    }
	}
}
