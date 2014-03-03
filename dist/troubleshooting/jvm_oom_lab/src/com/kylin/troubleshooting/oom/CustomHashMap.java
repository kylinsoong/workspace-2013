package com.kylin.troubleshooting.oom;

import java.util.HashMap;
import java.util.Map;

public class CustomHashMap extends HashMap {


	private static final long serialVersionUID = -3464252891005652599L;

	public CustomHashMap() {
	}

	public CustomHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	public CustomHashMap(Map m) {
		super(m);
	}

	public CustomHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
}
