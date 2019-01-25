# Packages for analysis
import pandas as pd
import numpy as np
from sklearn import preprocessing,model_selection,neighbors


# Packages for visuals
import matplotlib.pyplot as plt
import seaborn as sns; sns.set(font_scale=1.2)

# Allows charts to appear in the notebook
# %matplotlib inline

# Pickle package
import pickle

def svmfun(li):
	df = pd.read_csv('test.csv',sep = ",")
	df.drop(['lat'],1,inplace=True)
	df.drop(['lon'],1,inplace=True)
	df.replace('low',0,inplace = True)
	df.replace('medium',0,inplace = True)
	df.replace('high',1,inplace = True)
	# print(df)
	x = np.array(df.drop(['state'],1))
	y = np.array(df['state'])
	X_train,X_test,y_train,y_test = model_selection.train_test_split(x,y,test_size=0.2)
	clf = neighbors.KNeighborsClassifier()
	clf.fit(X_train,y_train)
	accuracy = clf.score(X_test,y_test)
	print(accuracy)

	# eg = np.array([[6.107299805,1.558029175,9.599807739],[4.717453003,5.854431152,16.94642639]])
	eg = np.array(li)
	eg = eg.reshape(len(eg),-1)
	prediction = clf.predict(eg)
	# print(prediction)
	return prediction
