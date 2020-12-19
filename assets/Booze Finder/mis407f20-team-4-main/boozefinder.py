from tkinter import *
from tkinter import messagebox
import tkinter
import sqlite3
import pandas 
import random
import csv_to_sqlite 


#INIT

def init():
    options = csv_to_sqlite.CsvOptions(typing_style="full", encoding="windows-1250") 
    input_files = ["alcohol_prices.csv"] 
    csv_to_sqlite.write_csv(input_files, "alcohol.sqlite", options)

#init()




#Vars
file = 'alcohol.sqlite'
connect = sqlite3.connect(file) 
c = connect.cursor()
table_name = 'alcohol_prices'

def determineQuality(dollars, type, numberOfBottles):
    budget = int(dollars)/int(numberOfBottles)
    if type != "whiskey" and type != "rum":
        if budget <= 20:
            return "budget"
        if 20 < budget <= 30:
            return "standard"
        if 30 < budget <= 100:
            return "premium"
        if 100 < budget:
            return "extreme"
    else:
        if budget <= 20:
            return "budget"
        if 20 < budget <= 40:
            return "standard"
        if 40 < budget <= 100:
            return "premium"
        if 100 < budget:
            return "extreme"


#Clears Input Data
def clearCommand():
    e1.delete(0,END)
    #e2.delete(0,END)
    e3.delete(0,END)
    #list1.delete(0,END)

def clearList():
    list1.delete(0,END)

#Quit dialog
def quit():
    MsgBox = tkinter.messagebox.askquestion ('Quit!','Are you sure you want to quit?',icon = 'warning')
    if MsgBox == 'yes':
       root.destroy()

def searchCommand():
    qual = determineQuality(e1.get(), typevar.get(), e3.get())
    usertype = typevar.get()
    price = int(e1.get())
    budget = float(price/int(e3.get()))
    c.execute("SELECT * FROM alcohol_prices WHERE Quality LIKE '{q}' AND AType like '{ut}' and Price < '{p}'".format(q=qual, ut=usertype, p=price))
    list1.insert(END, ("Budget/bottle: $", budget, "Searching for.... Quality: ", qual, " Alcohol Type:", usertype))
    list1.insert(END, ("You might enjoy...."))
    rows = c.fetchall()
    rando = random.choice(rows)
    name = rando[0]
    pprice = rando[1]
    list1.insert(END, (name, "Price: $",    pprice))


###GUI###

#init
root = Tk()
window = Frame(root)
window.grid(column=0, row=0, sticky=(N,W,E,S))
window.columnconfigure(0, weight=1)
window.rowconfigure(0, weight=1)
window.pack(pady=5, padx=5)
window.grid_columnconfigure(4, minsize=50)
window.grid_rowconfigure(4, minsize=200)


#Labels
l1=Label(window, text="Amount to Spend")
l1.grid(row=1,column=0)
l2=Label(window, text="Type of Alcohol")
l2.grid(row=1,column=2)
l3=Label(window, text="No. of Bottles")
l3.grid(row=1,column=4)
l4=Label(window, text=" ")
l4.grid(row=2,column=0)

#Entry boxes
amount_text=StringVar()
e1=Entry(window,textvariable=amount_text)
e1.grid(row=1,column=1)
type_text=StringVar()
#e2=Entry(window,textvariable=type_text)
#e2.grid(row=1,column=3)
bottles_text=StringVar()
e3=Entry(window,textvariable=bottles_text)
e3.grid(row=1,column=5)

#Pulldown menu
typevar = StringVar(window)
types = { 'whiskey', 'rum', 'vodka', 'gin', 'tequila'}
typevar.set('whiskey')
e2 = OptionMenu(window, typevar, *types)
e2.grid(row=1, column=3)

#Listbox/Scrollbar
list1=Listbox(window, height=6, width=100)
list1.grid(row=3, column=0, rowspan=6, columnspan=6)
sb1 = Scrollbar(window)
sb1.grid(row=3, column=7, rowspan=6, sticky=N+W+S)
list1.configure(yscrollcommand = sb1.set)
sb1.configure(command = list1.yview)

#Buttons
b1=Button(window, text="Search", width=10, command=searchCommand)
b1.grid(row=3, column=2)
b2=Button(window, text="Exit", width=10, command=quit)
b2.grid(row=11, column=5)
b3=Button(window,text="Clear",width=10, command=clearCommand)
b3.grid(row=3, column=3)
b4=Button(window,text="Clear Results",width=15, command=clearList)
b4.grid(row=11, column=3)

root.title("Booze Finder")

window.mainloop()

