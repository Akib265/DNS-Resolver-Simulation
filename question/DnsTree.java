package question;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Contains the methods and properties of a dns tree which keeps the nodes in
 * the right order.
 */
public class DnsTree {
	private DnsNode root;

	private Map<String, Set<String>> map = new TreeMap<String, Set<String>>();

	/**
	 * Creates a dns tree by creating a root node.
	 */
	public DnsTree() {
		root = new DnsNode();
	}

	public String queryDomain(String domainName) {
		DnsNode node = returnNode(domainName);
		if (node == null) {
			return null;
		} else {
			return node.requestIpAddress();
		}
	}


	public Map<String, Set<String>> getAllRecords() {
		ArrayList<String> arr = new ArrayList<>();
		traverseTree(root, arr);
		return map;
	}

	public void insertRecord(String domainName, String ipAddress) {
		StringTokenizer tokenizer = new StringTokenizer(domainName, ".", false);
		ArrayList<String> arr = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			arr.add(0, tokenizer.nextToken());
		}
		DnsNode current = root;
		for (int i = 0; i < arr.size(); i++) {
			if (current.getChildNodeList().containsKey(arr.get(i))) {
				current = current.getChildNodeList().get(arr.get(i));
				continue;
			} else {
				DnsNode node = new DnsNode();
				current.getChildNodeList().put(arr.get(i), node);
				current = node;
			}
		}
		current.addIp(ipAddress);
	}

	private DnsNode returnNode(String nodeName) {
		StringTokenizer tokenizer = new StringTokenizer(nodeName, ".", false);
		ArrayList<String> arr = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			arr.add(0, tokenizer.nextToken());
		}
		DnsNode current = root;
		for (int i = 0; i < arr.size(); i++) {
			if (current.getChildNodeList().containsKey(arr.get(i))) {
				current = current.getChildNodeList().get(arr.get(i));
				continue;
			} else {
				return null;
			}
		}
		return current;
	}

	private void traverseTree(DnsNode node, ArrayList<String> current) {
		if (node.getChildNodeList().isEmpty()) {
			return;
		} else {
			Iterator<Entry<String, DnsNode>> i = node.getChildNodeList().entrySet().iterator();
			while (i.hasNext()) {
				Entry<String, DnsNode> e = i.next();
				current.add(e.getKey());
				if (e.getValue().isValid()) {
					String temp = "";
					for (int j = 0; j < current.size(); j++) {
						temp = "." + current.get(j) + temp;
					}
					temp = temp.substring(1);
					map.put(temp, e.getValue().getIpAddresses());
				}
				traverseTree(e.getValue(), current);
				if (current.size() > 0) {
					current.remove(current.size() - 1);
				}
			}
		}
	}


	public DnsNode getRoot() {
		
		return this.root;
	}
}

