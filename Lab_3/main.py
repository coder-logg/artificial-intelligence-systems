#!/usr/bin/env python
from builtins import list
from draw_graphs import draw_graphs
from Mushroom import *
from sklearn.model_selection import train_test_split
from random import randint
from learn_functions import learn, proba

def read_mushrooms_from_file(file_name: str) -> list[Mushroom]:
	file = open(file_name, 'r')
	res: list[Mushroom] = []
	while True:
		line = file.readline().strip()
		if (line):
			splited = line.split(',')
			mushroom = Mushroom()
			mushroom.set_class(splited.pop(0))
			for i, field in enumerate(splited):
				mushroom.set_attr_by_index(i, field)
			res.append(mushroom)
		else:
			break
	return res

def get_column(mushrooms: list[Mushroom], column_name) -> list[str]:
	res: list[str] = []
	for mushroom in mushrooms:
		res.append(mushroom.get_attr_val(column_name))
	return res

def get_random_attrs(attrs_number):
	attrSet = set()
	for i in range(attrs_number):
		attr = Mushroom.get_attributes_names()[randint(0, 21)]
		if attr in attrSet:
			while attr in attrSet:
				attr = Mushroom.get_attributes_names()[randint(0, 21)]
		attrSet.add(attr)
	return attrSet

def recall(tp: int, fn: int) -> float:
	return tp / (tp + fn)

def accuracy(TP, TN, FP, FN) -> float:
	return (TP + TN) / (TP + FP + TN + FN)

def precision(TP, FP) -> float:
	return TP / (TP + FP)

list = read_mushrooms_from_file('agaricus-lepiota.data')
X_train, X_test, y_train, y_test = train_test_split(list, get_column(list, 'class'), test_size=0.2)


root = learn(X_train, get_random_attrs(int(22 ** (1 / 2))))

y_pred = y_test.copy()
rift = 0.5
TP, FP, TN, FN = 0, 0, 0, 0
for index, val in enumerate(X_test):
	origin_class = y_test[index]
	pred_class = proba(root, val)
	if origin_class == 'e' and pred_class >= rift:
		TP += 1
	if origin_class == 'e' and pred_class < rift:
		FN += 1
	if origin_class == 'p' and pred_class < rift:
		TN += 1
	if origin_class == 'p' and pred_class >= rift:
		FP += 1
	y_pred[index] = pred_class

print(f'count of samples {len(X_test)}\n\tT\tF\nP\t{TP}\t{FP}\nN\t{TN}\t{FN}')
print(f'accuracy is {accuracy(TP, TN, FP, FN)}')
print(f'precision is {precision(TP, FP)}')
print(f'recall is {recall(TP, FN)}')

results = []
for index, val in enumerate(y_pred):
	results.append((index, y_test[index], val))
results.sort(key=lambda x: (x[2]), reverse=True)
draw_graphs(results)
