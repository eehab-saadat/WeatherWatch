package org.algoavengers.weatherwatch.storage;

public class CacheManager {
    public CacheManagerInterface cache;

    public CacheManager(CacheManagerInterface cacheManager) {
        this.cache = cacheManager;
    }
}
