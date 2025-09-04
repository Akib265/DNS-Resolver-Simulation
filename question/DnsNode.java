

package question;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class DnsNode {
	private Map<String, DnsNode> childNodeList;

	private boolean validDomain;

	private Set<String> ipAddresses;

	/**
	 * Creates the fields of the class.
	 */
	public DnsNode() {
		childNodeList = new HashMap<String, DnsNode>();

		ipAddresses = new LinkedHashSet<String>();

		validDomain = false;
	}

	/**
	 * 
	 * @return the set of ip addresses of this node.
	 */
	public Set<String> getIpAddresses() {
		return ipAddresses;
	}

	/**
	 * Adds an ip address to the set of ip addresses.
	 * 
	 *  ip is the ip adress to be added
	 */
	public void addIp(String ip) {
		ipAddresses.add(ip);
		validDomain = true;
	}

	public boolean removeIpAddress(String ipAddress) {
		if (this.ipAddresses.contains(ipAddress)) {
			this.ipAddresses.remove(ipAddress);
			if (this.ipAddresses.isEmpty()) {
				validDomain = false;
			}
			return true;
		} else {
			return false;
		}
	}

	
	public boolean isValid() {
		return this.validDomain;
	}

	
	public String requestIpAddress() {
		if (this.validDomain && !ipAddresses.isEmpty()) {
			Iterator<String> i = ipAddresses.iterator();
			String next = i.next();
			i.remove();
			ipAddresses.add(next);
			return next;
		} else {
			return null;
		}
	}

	
	public Map<String, DnsNode> getChildNodeList() {
		return childNodeList;
	}
}

