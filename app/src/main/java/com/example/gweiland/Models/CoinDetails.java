package com.example.gweiland.Models;

import android.widget.ImageView;
import android.widget.TextView;

public class CoinDetails {
    public String getCoinIcon() {
        return coinIcon;
    }

    public void setCoinIcon(String coinIcon) {
        this.coinIcon = coinIcon;
    }

    public String getCoinAbbreviation() {
        return coinAbbreviation;
    }

    public void setCoinAbbreviation(String coinAbbreviation) {
        this.coinAbbreviation = coinAbbreviation;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(String coinPrice) {
        this.coinPrice = coinPrice;
    }

    public String getCoinDelta() {
        return coinDelta;
    }

    public void setCoinDelta(String coinDelta) {
        this.coinDelta = coinDelta;
    }

    String coinIcon,coinAbbreviation,coinName,coinPrice,coinDelta;

    public CoinDetails(String coinIcon, String coinAbbreviation, String coinName, String coinPrice, String coinDelta) {
        this.coinIcon = coinIcon;
        this.coinAbbreviation = coinAbbreviation;
        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.coinDelta = coinDelta;
    }
}
