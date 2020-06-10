package com.tclearned.xkcd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comic {
    @JsonProperty("img")
    String image;
}
