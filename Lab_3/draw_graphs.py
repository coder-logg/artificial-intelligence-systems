import matplotlib.pyplot as plt
import numpy as np

ORIG_CLASS, PRED_CLASS = 1, 2

def draw_graphs(results):
	x, y, tpr, fpr = calc_conversion(results)
	draw_auc_roc(fpr, tpr)
	draw_auc_pr(x, y)

def calc_conversion(results):
	list_recall, list_tpr, list_fpr, list_precise = [0], [0], [0], [1]
	number_p = sum([1 if i[ORIG_CLASS].__contains__('e') else 0 for i in results])
	number_n = sum([1 if not i[ORIG_CLASS].__contains__('e') else 0 for i in results])
	tp, fp = 0, 0
	if results[0][ORIG_CLASS]:
		tp = 1
	else:
		fp = 1
	for i in range(1, number_p + number_n):
		if str(results[i][ORIG_CLASS]).__contains__('e'):
			tp += 1
		else:
			fp += 1
		list_precise.append(tp / (tp + fp))
		list_recall.append(tp / number_p)
		list_tpr.append(tp / number_p)
		list_fpr.append(fp / number_n)
	# finish working convetion
	list_precise.append(0)
	list_recall.append(1)
	list_tpr.append(1)
	list_fpr.append(1)
	x = np.array(list_recall)
	y = np.array(list_precise)
	tpr = np.array(list_tpr)
	fpr = np.array(list_fpr)
	return [x, y, tpr, fpr]

def draw_auc_roc(fpr_arr, tpr_arr):
	plt.plot(np.linspace(0, 1, 2), np.linspace(0, 1, 2), linewidth=2.0)
	plt.plot(fpr_arr, tpr_arr, label='ROC', linewidth=2.0)
	plt.grid()
	plt.xlabel("False Positive Rate")
	plt.ylabel("True Positive Rate")
	plt.legend()
	plt.xlim(0, 1)
	plt.ylim(0, 1)
	plt.title("ROC curve")
	plt.show()


def draw_auc_pr(x_vals, y_vals):
	plt.plot(x_vals, y_vals, label='PR', linewidth=2)
	plt.grid()
	plt.title("PR curve")
	plt.legend()
	plt.xlim(0, 1)
	plt.ylim(0, 1)
	plt.xlabel("Recall")
	plt.ylabel("Precise")
	plt.show()
