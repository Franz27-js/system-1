package DaFraDa.dobot.awattar;

public class PriceEntry {
    private long start_timestamp;
    private long end_timestamp;
    private double marketprice;
    private String unit;
    private int hourIndex;

    // Getter/Setter
    public long getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(long start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public long getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(long end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public double getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(double marketprice) {
        this.marketprice = marketprice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public int getHourIndex() { 
    	return hourIndex; 
    }
    
    public void setHourIndex(int hourIndex) { 
    	this.hourIndex = hourIndex; 
    }

    @Override
    public String toString() {
        return "PriceEntry{" +
                "hour=" + hourIndex +
                ", start=" + new java.util.Date(start_timestamp) +
                ", end=" + new java.util.Date(end_timestamp) +
                ", price=" + marketprice + " " + unit +
                '}';
    }
}