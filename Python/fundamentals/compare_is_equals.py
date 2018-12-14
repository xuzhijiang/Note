list1 = []
list2 = []
list3 = list1

# ==是对比值
if(list1 == list2):
	print("list1 == list2 ? %s" % (list1 == list2))
else:
	print("list1 == list2 ? %s" % (list1 == list2))

# is是对比地址
if(list1 is list2):
	print("True")
else:
	print("False")

if(list1 is list3):
	print("True")
else:
	print("False")