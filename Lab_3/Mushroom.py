class Mushroom:

	def __init__(self):
		self.clasz: str
		self.attributes: dict[str, str] = {}

	@classmethod
	def attrs_allowed_values(cls):
		return {'cap_shape': 'bcxfks', 'cap_surface': 'fgys', 'cap_color': 'nbcgrpuewy', 'bruises': 'tf',
				'odor': 'alcyfmnps', 'gill_attachment': 'adfn', 'gill_spacing': 'cwd', 'gill_size': 'bn',
				'gill_color': 'knbhgropuewy', 'stalk_shape': 'et', 'stalk_root': 'bcuezr?',
				'stalk_surface_above_ring': 'fyks', 'stalk_surface_below_ring': 'fyks',
				'stalk_color_above_ring': 'nbcgopewy', 'stalk_color_below_ring': 'nbcgopewy', 'veil_type': 'pu',
				'veil_color': 'nowy', 'ring_number': 'not', 'ring_type': 'ceflnpsz', 'spore_print_color': 'knbhrouwy',
				'population': 'acnsvy', 'habitat': 'glmpuwd'}

	@classmethod
	def get_attributes_names(cls) -> list[str]:
		return ['cap_shape', 'cap_surface', 'cap_color', 'bruises', 'odor', 'gill_attachment',
				'gill_spacing', 'gill_size', 'gill_color', 'stalk_shape', 'stalk_root', 'stalk_surface_above_ring',
				'stalk_surface_below_ring', 'stalk_color_above_ring', 'stalk_color_below_ring', 'veil_type',
				'veil_color', 'ring_number', 'ring_type', 'spore_print_color', 'population', 'habitat']

	def set_class(self, val: str):
		if 'pe'.__contains__(val):
			self.clasz = val
		else:
			raise ValueError('unknown class value: \'' + val + '\'')

	def get_class(self):
		return self.clasz

	def set_attr(self, attr: str, value: str) -> 'Mushroom':
		if value in Mushroom.attrs_allowed_values()[attr]:
			self.attributes[attr] = value
		else:
			raise ValueError('unknown attr value: ' + attr.name + ' = \'' + value + '\'')
		return self

	def set_attr_by_index(self, index: int, value: str) -> 'Mushroom':
		return self.set_attr(Mushroom.get_attributes_names()[index], value)

	def get_attr_val(self, attr_name: str):
		if attr_name == 'class' or attr_name == 'clas':
			return self.clasz
		return self.attributes[attr_name][0]

	@classmethod
	def get_valid_attr_values(cls, attr_name: str) -> str:
		if attr_name == 'class' or attr_name == 'clas':
			return 'pe'
		return cls.attrs_allowed_values()[attr_name]

	def get_attr_val_by_index(self, attr_index: int):
		return self.get_attr_val(Mushroom.get_attributes_names()[attr_index])

	def __str__(self):
		res = '{\'class\': \'' + self.clasz + '\', '
		for elm in self.attributes:
			res += '\'' + elm + '\': \'' + self.attributes[elm] + '\', '
		return res + '}'

	def __repr__(self):
		return self.__str__()


def get_elems_with_given_attr_value(mushrooms: list[Mushroom], attr_name, attr_val) -> list[Mushroom]:
	res: list[Mushroom] = []
	for mushroom in mushrooms:
		if mushroom.get_attr_val(attr_name) == attr_val:
			res.append(mushroom)
	return res

def count_attr_in_column(mushrooms: list[Mushroom], attr_name: str, attr_value: str) -> int:
	res = 0
	for mushroom in mushrooms:
		if mushroom.get_attr_val(attr_name) == attr_value:
			res += 1
	return res

def get_subset_by_attr_value(mushrooms: list[Mushroom], attr_name, attr_value):
	res_list = []
	for mushroom in mushrooms:
		if mushroom.get_attr_val(attr_name) == attr_value:
			res_list.append(mushroom)
	return res_list
