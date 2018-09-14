import requests as rq
import re
from bs4 import BeautifulSoup

mainPage = rq.get('https://arxiv.org/list/cs/new').text
bsMainPage = BeautifulSoup(mainPage, 'html.parser')

abstracts = bsMainPage.find_all('a', title = 'Abstract')
parse = lambda a: { 'title': a.text, 'link': 'https://arxiv.org%s' % (a.get('href')) } 

abstracts = [parse(a) for a in abstracts]

for a in abstracts:
    try:
        page = rq.get(a['link'])
    except:
        print('did not get this page: %s' % (a['link']))
        continue
    file = open('data/computerScience/%s.txt' % (a['title']), 'w')
    file.write(page.text)
    file.close()