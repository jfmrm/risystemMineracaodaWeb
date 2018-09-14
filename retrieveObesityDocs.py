import requests as rq
import csv
import re

with open('data/links.csv') as f:
    reader = csv.reader(f, delimiter = ';')
    links = list(reader)

i = 0
for link in links:
    if re.search('\/search\?q\=', link[1]):
       continue 
    try:
        page = rq.get(re.sub('\/url\?q\=|\/search\?q\=', '', link[1]))
    except:
        print('did not got this page:\n%s', link[1])
        continue
    # print(link[1], re.sub('\/url\?q\=|\/search\?q\=', '', link[1]))
    file = open('data/obesity/%s.txt' % (link[0]), 'w')
    file.write(page.text)
    file.close()
    i += 1

print(i)