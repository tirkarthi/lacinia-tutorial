from __future__ import print_function
import json
import sqlite3

db = sqlite3.connect("test.db")
cursor = db.cursor()

print("Creating tables")

cursor.execute('''
CREATE TABLE artist(
  artistid    INTEGER PRIMARY KEY AUTOINCREMENT,
  artistname  TEXT
);''')

cursor.execute('''
CREATE TABLE track(
  trackid     INTEGER,
  trackname   TEXT,
  trackartist INTEGER     -- Must map to an artist.artistid!
);''')

print("Filling up values")

with open('artists.json') as f:
    data = json.loads(f.read())
    cursor.executemany('''
    insert into artist (artistid, artistname)
    values (:artistid, :artistname)
    ''', data)

with open('tracks.json') as f:
    data = json.loads(f.read())
    cursor.executemany('''
    insert into track (trackid, trackname, trackartist)
    values (:trackid, :trackname, :trackartist)
    ''', data)

print("Creating .lein-env for testing")
with open('.lein.env', 'w') as f:
    f.write('''{:env {:database-url "test.db"}}''')

db.commit()
cursor.close()
db.close()
