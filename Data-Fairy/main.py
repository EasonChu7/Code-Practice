"""
@author: Easonchu777
"""
from PyQt5 import QtWidgets,QtGui
from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import  pyqtSignal
from PyQt5.QtCore import QThread
from PyQt5.QtWidgets import QDialog, QApplication, QMainWindow,QMessageBox
import re
import sys
import os
import numpy as np
import matplotlib.pyplot as plt

from sklearn.linear_model import LinearRegression
from sklearn import datasets
import sys
import time

import numpy as np

from matplotlib.backends.qt_compat import QtCore, QtWidgets, is_pyqt5
if is_pyqt5():
    from matplotlib.backends.backend_qt5agg import (
        FigureCanvas, NavigationToolbar2QT as NavigationToolbar)
else:
    from matplotlib.backends.backend_qt4agg import (
        FigureCanvas, NavigationToolbar2QT as NavigationToolbar)
from matplotlib.figure import Figure





popularity = [221478,	219830,	216921,	215015,	212132,	209287,	206899,	204579,	201543,	199928,	198285,	196305,	194318,	191884,	189367,	186876,	185183,	182142,	180389,	175230,	173754,	172211,	170581,	168323,	166438,	164512,	162826,	160827,	158694,	156538,	154155,	151635,	149144,	146606,	143524,	140794,	138020,	135526,	132899,	130409,	125295,	123019,	120870,	118848,	116897,	115005,	112866,	111133,	110627,	109310,	107750,	106469,	104864,	103876,	103245,	102421,	100664,	99465,	97478,	95630,	88358,	87484,	82578,	74776]
popularity = np.array(popularity)
year = [2019,	2018,	2017,	2016,	2015,	2014,	2013,	2012,	2011,	2010,	2009,	2008,	2007,	2006,	2005,	2004,	2003,	2002,	2001,	2000,	1999,	1998,	1997,	1996,	1995,	1994,	1993,	1992,	1991,	1990,	1989,	1988,	1987,	1986,	1985,	1984,	1983,	1982,	1981,	1980,	1979,	1978,	1977,	1976,	1975,	1974,	1973,	1972,	1971,	1970,	1969,	1968,	1967,	1966,	1965,	1964,	1962,	1960,	1959,	1957,	1952,	1950,	1947,	1940]
year = np.array(year)
year = year.reshape(-1,1)
popularity = popularity.reshape(-1,1)

model = LinearRegression()
model.fit(year,popularity)
predict = model.predict(year[:200,:])




class ApplicationWindow(QtWidgets.QMainWindow):
    
    def __init__(self):
        super().__init__()
        self._main = QtWidgets.QWidget()
        self.setCentralWidget(self._main)
        layout = QtWidgets.QVBoxLayout(self._main)
        

        
        
        
        self.setWindowTitle('DataFairy-v0.0.0-betav1')
        image = QtGui.QPixmap()
        image.load('pic/image-24.png')
        image = image.scaled(self.width(), self.height())

        self.setWindowIcon(QtGui.QIcon('pic/logo2.jpg'))

        static_canvas = FigureCanvas(Figure(figsize=(5, 3)))
        layout.addWidget(static_canvas)
        self.addToolBar(NavigationToolbar(static_canvas, self))

        dynamic_canvas = FigureCanvas(Figure(figsize=(5, 3)))
        layout.addWidget(dynamic_canvas)
        self.addToolBar(QtCore.Qt.BottomToolBarArea,
                        NavigationToolbar(dynamic_canvas, self))

        self._static_ax = static_canvas.figure.subplots()
        self._static_ax.plot(year,predict,'red')
        self._static_ax.scatter(year,popularity)

        self._dynamic_ax = dynamic_canvas.figure.subplots()
        self._timer = dynamic_canvas.new_timer(
            100, [(self._update_canvas, (), {})])
        self._timer.start()

    def _update_canvas(self):
        self._dynamic_ax.clear()
        t = np.linspace(0, 10, 101)
        # Shift the sinusoid as a function of time.
        self._dynamic_ax.plot(t, np.sin(t + time.time()))
        self._dynamic_ax.figure.canvas.draw()


if __name__ == "__main__":
    qapp = QtWidgets.QApplication(sys.argv)
    app = ApplicationWindow()
    app.show()
    qapp.exec_()


