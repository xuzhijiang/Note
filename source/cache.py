from threading import local

from django.conf import settings
from django.core import signals
from django.core.cache.backends.base import (
    BaseCache, CacheKeyWarning, InvalidCacheBackendError,
)
from django.utils.module_loading import import_string

__all__ = [
    'cache', 'DEFAULT_CACHE_ALIAS', 'InvalidCacheBackendError',
    'CacheKeyWarning', 'BaseCache',
]

DEFAULT_CACHE_ALIAS = 'default'

TESTING = True

def _create_cache(backend, **kwargs):
    try:
        # Try to get the CACHES entry for the given backend name first
        try:
            conf = {
			        'BACKEND': 'django.core.cache.backends.memcached.MemcachedCache',
			        'LOCATION': '127.0.0.1:11211',
			        'KEY_PREFIX': 'django_test' if TESTING else 'djangoblog',
			        'TIMEOUT': 60 * 60 * 10
			    	}
        except KeyError:
            try:
                # Trying to import the given backend, in case it's a dotted path
                import_string(backend)
            except ImportError as e:
                raise InvalidCacheBackendError("Could not find backend '%s': %s" % (
                    backend, e))
            location = kwargs.pop('LOCATION', '')
            params = kwargs
        else:
            params = conf.copy()
            params.update(kwargs)
            backend = params.pop('BACKEND')
            location = params.pop('LOCATION', '')
        backend_cls = import_string(backend)
    except ImportError as e:
        raise InvalidCacheBackendError(
            "Could not find backend '%s': %s" % (backend, e))
    return backend_cls(location, params)

class CacheHandler:
	"""
	"""
	def __init__(self):
		self._caches = local()

	def __getitem__(self, alias):
		print('__getitem__ alias: %s' % alias)
		try:
			return self._caches.caches[alias]
		except AttributeError:
			print('catch AttributeError')
			self._caches.caches = {}
		except KeyError:
			pass

		cache = _create_cache(alias)
		self._caches.caches[alias] = cache
		return cache

	def all(self):
		return getattr(self._caches, 'caches', {}).values()

caches = CacheHandler()

class DefaultCacheProxy:
	
	def __getattr__(self, name):
		print('__getattr__---name: %s' % name)
		return getattr(caches[DEFAULT_CACHE_ALIAS], name)

	def __setattr__(self, name, value):
		return setattr(caches[DEFAULT_CACHE_ALIAS], name, value)

	def __delattr__(self, name):
		return delattr(caches[DEFAULT_CACHE_ALIAS], name)

	def __contains__(self, key):
		return key in caches[DEFAULT_CACHE_ALIAS]

	def __eq__(self, other):
		return caches[DEFAULT_CACHE_ALIAS] == other

cache = DefaultCacheProxy()

def close_caches(**kwargs):
	for cache in caches.all():
		cache.close()

value = cache.set('get_blog_setting', 'value')
print(value)