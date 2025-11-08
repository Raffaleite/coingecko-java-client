package coingecko;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Crypto {

    public String id;
    public String symbol;
    public String name;

    @JsonProperty("current_price")
    public double currentPrice;

    @JsonProperty("market_cap")
    public double marketCap;

    @JsonProperty("image")
    public String imageUrl;

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return name + " (" + symbol.toUpperCase() + ")";
    }
}
