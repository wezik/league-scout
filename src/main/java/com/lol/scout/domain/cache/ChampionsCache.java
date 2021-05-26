package com.lol.scout.domain.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "champions_cache")
public class ChampionsCache {
    @Id
    private LocalDate lastUpdate;
    @Lob
    private String json;
}