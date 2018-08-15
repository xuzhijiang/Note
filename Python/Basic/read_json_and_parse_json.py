from urllib import request
import json

url = 'https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20%3D%202151330&format=json'

def fetch_data(url):
	with request.urlopen(url) as f:
		status = f.status
		print('status:', status)
		if status == 200:
			data = f.read().decode('utf-8')
		if data is not None:
			return json.loads(data)
		else:
			return None

data = fetch_data(url)
print(data)
if data is not None:
	try:
		assert data['query']['results']['channel']['location']['city'] == 'Beijing'
		print('ok')
	except AssertionError:
		print('some assert error happens!')