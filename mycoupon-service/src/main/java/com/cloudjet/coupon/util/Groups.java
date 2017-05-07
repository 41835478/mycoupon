package com.cloudjet.coupon.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import ch.lambdaj.Lambda;
import ch.lambdaj.group.Group;
import ch.lambdaj.group.GroupCondition;

public class Groups {
	
	public static <T> List<List<T>> group(List<T> iterator,
			String... properties) {
		List<List<T>> data = Lists.newArrayList();
		Group<T> groups = Lambda.group(iterator, properties);
		addOrFind(groups, data);
		return data;
	}
	
	public static <T> List<List<T>> group(List<T> iterator, GroupCondition<?>... conditions) {
		List<List<T>> data = Lists.newArrayList();
		Group<T> groups = Lambda.group(iterator, conditions);
		addOrFind(groups, data);
		return data;
	}

	public static List<List<Map<String, Object>>> groupMap(
			List<Map<String, Object>> iterator, String... properties) {
		List<List<Map<String, Object>>> data = Lists.newArrayList();
		@SuppressWarnings("rawtypes")
		GroupCondition[] conditions = new GroupCondition[properties.length];
		int index = 0;
		for (String property : properties) {
			conditions[index++] = Lambda.by((Lambda.on(new HashMap<String, Object>().getClass())).get(property));
		}
		Group<Map<String, Object>> groups = Lambda.group(iterator, conditions);
		addOrFind(groups, data);
		return data;
	}

	private static <T> void addOrFind(Group<T> group, List<List<T>> data) {
		boolean flag = group.isLeaf();
		if (!flag) {
			List<Group<T>> list = group.subgroups();
			for (Group<T> groups : list) {
				addOrFind(groups, data);
			}
		} else {
			data.add(group.findAll());
		}
	}
	
}
