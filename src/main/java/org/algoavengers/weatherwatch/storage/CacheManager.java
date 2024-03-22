package org.algoavengers.weatherwatch.storage;

public class CacheManager {
    CacheManagerInterface cache;

    public CacheManager(CacheManagerInterface cacheManager) {
        this.cache = cacheManager;
    }
}
