package com.moome;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
	public static final List<String> index(final List<String> all, final String keyword) {
		final List<String> indexlist = new ArrayList<>();
		for(final String s : all) {
			String l = s.toLowerCase();
			String k = keyword.toLowerCase();
			if(l.contains(k)) {
				indexlist.add(s);
			}
			l = null;
			k = null;
		}
		return indexlist;
	}
}
