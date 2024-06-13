package cloud7c.speedrunrecorder.model;

public class Preferences {
    private boolean generateAverage;
    private boolean generateCurrent;
    private boolean generateCount;
    private boolean generateRecords;
    private boolean loadHistoryRecords;
    private boolean renderBestRecord;
    private boolean renderAverageRecord;
    private int refreshInterval;
    
    public Preferences(boolean generateAverage, boolean generateCurrent, boolean generateCount, boolean generateRecords, boolean loadHistoryRecords, boolean renderBestRecord, boolean renderAverageRecord, int refreshInterval){
        this.generateAverage = generateAverage;
        this.generateCurrent = generateCurrent;
        this.generateCount = generateCount;
        this.generateRecords = generateRecords;
        this.loadHistoryRecords = loadHistoryRecords;
        this.renderBestRecord = renderBestRecord;
        this.renderAverageRecord = renderAverageRecord;
        this.refreshInterval = refreshInterval;
    }

    public Preferences(){
        this(true, true, true, true, true, true, true, 5);
    }
    
    public boolean getGenerateAverage(){
        return generateAverage;
    }
    
    public boolean getGenerateCurrent(){
        return generateCurrent;
    }
    
    public boolean getGenerateCount(){
        return generateCount;
    }

    public boolean getGenerateRecords(){
        return generateRecords;
    }

    public boolean getLoadHistoryRecords(){
        return loadHistoryRecords;
    }

    public boolean getRenderBestRecord(){
        return renderBestRecord;
    }

    public boolean getRenderAverageRecord(){
        return renderAverageRecord;
    }
    public int getRefreshInterval(){
        return refreshInterval;
    }
    
    public void setGenerateAverage(boolean generateAverage){
        this.generateAverage = generateAverage;
    }
    
    public void setGenerateCurrent(boolean generateCurrent){
        this.generateCurrent = generateCurrent;
    }
    
    public void setGenerateCount(boolean generateCount){
        this.generateCount = generateCount;
    }

    public void setGenerateRecords(boolean generateRecords){
        this.generateRecords = generateRecords;
    }

    public void setLoadHistoryRecords(boolean loadHistoryRecords){
        this.loadHistoryRecords = loadHistoryRecords;
    }

    public void setRefreshInterval(int refreshInterval){
        this.refreshInterval = refreshInterval;
    }

    public void setRenderBestRecord(boolean renderBestRecord){
        this.renderBestRecord = renderBestRecord;
    }

    public void setRenderAverageRecord(boolean renderAverageRecord){
        this.renderAverageRecord = renderAverageRecord;
    }
}
