from Mushroom import *
from Node import Node
import numpy as np
import math

def calc_entropy(mushrooms: list[Mushroom]):
	res2 = 0.0
	allowed_values = Mushroom.get_valid_attr_values('clas')
	for val in allowed_values:
		p = float(count_attr_in_column(mushrooms, 'clas', val) / float(len(mushrooms)))
		if p == 0:
			continue
		res2 += p * math.log(p, 2)
	return res2

def calc_conditional_entropy(mushrooms: list[Mushroom], attr_name: str):
	res = 0.0
	allowed_values = Mushroom.get_valid_attr_values(attr_name)
	for val in allowed_values:
		subset = get_subset_by_attr_value(mushrooms, attr_name, val)
		if len(subset) == 0:
			continue
		res += (float(len(subset)) / len(mushrooms)) * calc_entropy(subset)
	return res

def split_info(mushrooms: list[Mushroom], attr_name: str):
	res = 0.0
	allowed_values = Mushroom.get_valid_attr_values(attr_name)
	for val in allowed_values:
		p = float(count_attr_in_column(mushrooms, attr_name, val) / float(len(mushrooms)))
		if p == 0:
			continue
		res += p * math.log(p, 2)
	return -res

def gain(mushrooms: list[Mushroom], attr_name: str):
	_split_info = float(split_info(mushrooms, attr_name))
	if abs(_split_info) < np.finfo(float).eps:
		return -math.inf
	return float(float(calc_entropy(mushrooms) - calc_conditional_entropy(mushrooms, attr_name)) / _split_info)

def get_max_gain_attr(mushrooms: list[Mushroom], attrs: set[str]) -> str:
	max_gain = -math.inf
	attr_with_max_gain = None
	for index, attr in enumerate(attrs):
		gr = gain(mushrooms, attr)
		if max_gain < gr:
			max_gain = gr
			attr_with_max_gain = attr
	return attr_with_max_gain

def learn(mushrooms: list[Mushroom], attr_set: set[str]) -> Node:
	root = Node({}, None, mushrooms)
	nodes: list[Node] = [root]
	i = 0
	while i < len(nodes):
		node: Node = nodes[i]
		i += 1
		x = node.members
		if len(x) < 2:
			continue

		attrs = attr_set - node.get_attrs()
		result_attr = get_max_gain_attr(x, attrs)

		if result_attr == None:
			continue
		node.attr = result_attr

		for value in Mushroom.get_valid_attr_values(result_attr):
			rows_with_value = get_elems_with_given_attr_value(x, result_attr, value)
			child: Node = Node({}, node, rows_with_value)
			nodes.append(child)
			node.childs[value] = child
	return root

def proba(root: Node, sample: Mushroom) -> float:
	node = root
	while node.attr is not None:
		val = sample.get_attr_val(node.attr)
		if val not in node.childs.keys():
			break
		node = node.childs[val]
	return len(get_elems_with_given_attr_value(node.members, 'class', 'e'))/len(node.members)