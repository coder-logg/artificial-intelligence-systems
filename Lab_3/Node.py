from Mushroom import Mushroom

class Node:
	def __init__(self, childs: dict[str, 'Node' or None], parent: 'Node' or None, members: list[Mushroom], attr=None):
		self.members: list[Mushroom] = members
		self.childs: dict[str, 'Node' or None] = childs
		self.parent: 'Node' or None = parent
		self.attr: str = attr

	def add_children(self, node: 'Node'):
		node.parent = self
		self.childs.append(node)

	def get_attrs(self) -> set[str]:
		attrs = set()
		if self.attr is not None:
			attrs.add(self.attr)
		node = self
		while node.parent is not None:
			node = node.parent
			if node.attr is not None:
				attrs.add(node.attr)
		return attrs

	def __str__(self) -> str:
		return "{childs: " + self.childs.__str__() + \
				", attr: " + self.attr + "}"
				# ", parent: " + \
				# ", members:" + self.members.__str__() + \
