package com.lol.scout.manager;

import com.google.gson.GsonBuilder;
import com.lol.scout.domain.champion.ChampionListDto;
import com.lol.scout.domain.cache.ChampionsCache;
import com.lol.scout.domain.cache.LanguagesCache;
import com.lol.scout.domain.cache.VersionsCache;
import com.lol.scout.exception.ApiFetchFailedException;
import com.lol.scout.mapper.DataCacheMapper;
import com.lol.scout.service.ApiDataService;
import com.lol.scout.service.ChampionsCacheService;
import com.lol.scout.service.LanguagesCacheService;
import com.lol.scout.service.VersionsCacheService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCacheManager.class);

    private final LanguagesCacheService languagesCacheService;
    private final VersionsCacheService versionsCacheService;
    private final DataCacheMapper dataCacheMapper;

    private final ApiDataService apiDataService;

    public Optional<LanguagesCache> getLanguagesFromCache() {
        List<LanguagesCache> entries = languagesCacheService.getAll();
        if (entries.size() >= 1) {
            return Optional.of(entries.get(0));
        }
        return Optional.empty();
    }

    public Optional<LanguagesCache> cacheLanguagesFromApi() {
        List<String> apiResponse = apiDataService.getLanguages();
        if (apiResponse.isEmpty()) return Optional.empty();
        LanguagesCache cache = dataCacheMapper.mapListToLanguagesCache(apiResponse,LocalDate.now());
        languagesCacheService.deleteAll();
        LanguagesCache result = languagesCacheService.save(cache);
        logCaching();
        return Optional.of(result);
    }

    public Optional<VersionsCache> getVersionsFromCache() {
        List<VersionsCache> entries = versionsCacheService.getAll();
        if (entries.size() >= 1) {
            return Optional.of(entries.get(0));
        }
        return Optional.empty();
    }

    public Optional<VersionsCache> cacheVersionsFromApi() {
        List<String> apiResponse = apiDataService.getVersions();
        if (apiResponse.isEmpty()) return Optional.empty();
        VersionsCache cache = dataCacheMapper.mapListToVersionsCache(apiResponse,LocalDate.now());
        versionsCacheService.deleteAll();
        VersionsCache result = versionsCacheService.save(cache);
        logCaching();
        return Optional.of(result);
    }

    private void logCaching() {
        LOGGER.info("Data was cached");
    }

}
