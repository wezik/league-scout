package com.lol.scout.domain.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "champions_cache")
public class ChampionsCache {
    @Id
    private String patch;
    @Lob
    private String json;
}
