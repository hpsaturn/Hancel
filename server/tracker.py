import redis
import hashlib

r = redis.StrictRedis(host='localhost', port=6379, db=0)

m = hashlib.md5()


print(str(r.smembers('trace_'+'8f14e45fceea167a5a36dedd4bea2543')))


