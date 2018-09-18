import requests as rq
from bs4 import BeautifulSoup

url = 'https://www.google.com.br/search?q=obesity&ei=eMuaW4fhNsn7wQTR5L_gAg&start=%d&sa=N&biw=1280&bih=496'

search = []
for i in range(10):
    page = rq.get(url % (i*10))
    page = BeautifulSoup(page.text, 'html.parser')
    search += page.find_all('h3', 'r')
    nextPage = page.find('div', id = 'foot').find('a')
   
getLinks = lambda p: dict({ 'title': p.find('a').text, 'link': p.find('a').get('href') })

links = [getLinks(p) for p in search]

file = open('data/links.csv', 'w')
for link in links:
    file.write('%s; %s\n' % (link['title'], link['link']))
file.close()