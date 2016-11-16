package com.maxifly.vapi.model;

public class AuthHeader {
    private String ip_h;
    private String lg_h;
    private String to;
    private String origin;

    public AuthHeader(final String ip_h, final String to, final String lg_h, final String origin) {
        this.ip_h = ip_h;
        this.lg_h = lg_h;
        this.to = to;
        this.origin = origin;
    }

    public String getIp_h() {
        return ip_h;
    }

    public String getTo() {
        return to;
    }

    public String getLg_h() {
        return lg_h;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "AuthHeader{" +
                "ip_h='" + ip_h + '\'' +
                ", lg_h='" + lg_h + '\'' +
                ", to='" + to + '\'' +
                '}';
    }


}
