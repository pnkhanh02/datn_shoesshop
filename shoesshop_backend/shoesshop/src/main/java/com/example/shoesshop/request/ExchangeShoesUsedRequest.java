package com.example.shoesshop.request;

public class ExchangeShoesUsedRequest {
    private boolean used;

    public ExchangeShoesUsedRequest() {
    }

    public ExchangeShoesUsedRequest(boolean used) {
        this.used = used;
    }

    public boolean getUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
