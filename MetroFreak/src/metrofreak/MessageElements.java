package metrofreak;


import javafx.beans.property.SimpleStringProperty;


public class MessageElements {
    public String name;
    public String description;
    public String guid;
    public String physicalAddress;
    public String state;
    public String ssid;
    public String bssid;
    public String networkType;
    public String radioType;
    public String authentification;
    public String cipher;
    public String channel;
    public String receiveRateMbps;
    public String transmitRateMbps;
    public String signal;
    public String profile;
    
    public MessageElements() {}
    
    public MessageElements(String xName, String xDesc, String xGuide, String xPhAddress, String xState, String xSsid,
            String xBssid, String xNetw, String xRadio, String xAuth, String xCipher, 
            String xChannel, String xReceiveRate, String xTransmitRate, String xSignal, String xProfile) {
               
        this.name = xName;
        this.description = xDesc;
        this.guid = xGuide;
        this.physicalAddress = xPhAddress;
        this.state = xState;
        this.ssid = xSsid;
        this.bssid = xBssid;
        this.networkType = xNetw;
        this.radioType = xRadio;
        this.authentification = xAuth;
        this.cipher = xCipher;
        this.channel = xChannel;
        this.receiveRateMbps = xReceiveRate;
        this.transmitRateMbps = xTransmitRate;
        this.signal = xSignal;
        this.profile = xProfile;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getRadioType() {
        return radioType;
    }

    public void setRadioType(String radioType) {
        this.radioType = radioType;
    }

    public String getAuthentification() {
        return authentification;
    }

    public void setAuthentification(String authentification) {
        this.authentification = authentification;
    }

    public String getCipher() {
        return cipher;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReceiveRateMbps() {
        return receiveRateMbps;
    }

    public void setReceiveRateMbps(String receiveRateMbps) {
        this.receiveRateMbps = receiveRateMbps;
    }

    public String getTransmitRateMbps() {
        return transmitRateMbps;
    }

    public void setTransmitRateMbps(String transmitRateMbps) {
        this.transmitRateMbps = transmitRateMbps;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    
}
