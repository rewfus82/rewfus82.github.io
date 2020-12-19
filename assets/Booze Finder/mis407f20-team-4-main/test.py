import sqlite3
sqlite_file = 'alcohol.sqlite'
table_name = 'alcoholprices'
conn = sqlite3.connect(sqlite_file)
cur = conn.cursor()
cur.execute("SELECT * FROM {tn} WHERE type='Whiskey' ".format(tn = table_name))
all_rows = cur.fetchall()
print( all_rows)

conn.close()

