import os

path = os.getcwd() + "/"
allFiles = os.listdir(path)

for i in allFiles:
	name = i[:-4]
	if i[-4:] == ".png":
		print("\"cg:" + name + "\": \"" + i + "\",")
