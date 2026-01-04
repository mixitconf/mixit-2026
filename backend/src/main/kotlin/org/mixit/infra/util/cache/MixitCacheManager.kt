package org.mixit.infra.util.cache

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component

@Component
class MixitCacheManager(
    private val cacheManager: CacheManager,
) {
    fun clearCache() {
        cacheManager.cacheNames.forEach { cacheName ->
            cacheManager.getCache(cacheName)?.clear()
        }
    }

    fun evictCache(cacheName: String) {
        cacheManager.getCache(cacheName)?.clear()
    }
}
