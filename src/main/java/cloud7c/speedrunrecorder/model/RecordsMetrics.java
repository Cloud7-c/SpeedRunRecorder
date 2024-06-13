package cloud7c.speedrunrecorder.model;

public class RecordsMetrics {
    private Record bestRecord = new Record();

    private int enteredNetherCount = 0;
    private long enteredNetherTime = 0;
    private int enteredBastionCount = 0;
    private long enteredBastionTime = 0;
    private int enteredFortressCount = 0;
    private long enteredFortressTime = 0;
    private int firstPortalCount = 0;
    private long firstPortalTime = 0;
    private int enteredStrongholdCount = 0;
    private long enteredStrongholdTime = 0;
    private int enteredEndCount = 0;
    private long enteredEndTime = 0;
    private int finishedCount = 0;
    private long finishedTime = 0;

    public Record getBestRecord() {
        return bestRecord;
    }
    public void setBestRecord(Record bestRecord) {
        this.bestRecord = bestRecord;
    }

    public int getEnteredNetherCount() {
        return enteredNetherCount;
    }

    public void setEnteredNetherCount(int enteredNetherCount) {
        this.enteredNetherCount = enteredNetherCount;
    }

    public long getEnteredNetherTime() {
        return enteredNetherTime;
    }

    public void setEnteredNetherTime(long enteredNetherTime) {
        this.enteredNetherTime = enteredNetherTime;
    }

    public int getEnteredBastionCount() {
        return enteredBastionCount;
    }

    public void setEnteredBastionCount(int enteredBastionCount) {
        this.enteredBastionCount = enteredBastionCount;
    }

    public long getEnteredBastionTime() {
        return enteredBastionTime;
    }

    public void setEnteredBastionTime(long enteredBastionTime) {
        this.enteredBastionTime = enteredBastionTime;
    }

    public int getEnteredFortressCount() {
        return enteredFortressCount;
    }

    public void setEnteredFortressCount(int enteredFortressCount) {
        this.enteredFortressCount = enteredFortressCount;
    }

    public long getEnteredFortressTime() {
        return enteredFortressTime;
    }

    public void setEnteredFortressTime(long enteredFortressTime) {
        this.enteredFortressTime = enteredFortressTime;
    }

    public int getFirstPortalCount() {
        return firstPortalCount;
    }

    public void setFirstPortalCount(int firstPortalCount) {
        this.firstPortalCount = firstPortalCount;
    }

    public long getFirstPortalTime() {
        return firstPortalTime;
    }

    public void setFirstPortalTime(long firstPortalTime) {
        this.firstPortalTime = firstPortalTime;
    }

    public int getEnteredStrongholdCount() {
        return enteredStrongholdCount;
    }

    public void setEnteredStrongholdCount(int enteredStrongholdCount) {
        this.enteredStrongholdCount = enteredStrongholdCount;
    }

    public long getEnteredStrongholdTime() {
        return enteredStrongholdTime;
    }

    public void setEnteredStrongholdTime(long enteredStrongholdTime) {
        this.enteredStrongholdTime = enteredStrongholdTime;
    }

    public int getEnteredEndCount() {
        return enteredEndCount;
    }

    public void setEnteredEndCount(int enteredEndCount) {
        this.enteredEndCount = enteredEndCount;
    }

    public long getEnteredEndTime() {
        return enteredEndTime;
    }

    public void setEnteredEndTime(long enteredEndTime) {
        this.enteredEndTime = enteredEndTime;
    }

    public int getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(int finishedCount) {
        this.finishedCount = finishedCount;
    }

    public long getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(long finishedTime) {
        this.finishedTime = finishedTime;
    }

    public void reset() {
        enteredNetherCount = 0;
        enteredNetherTime = 0;
        enteredBastionCount = 0;
        enteredBastionTime = 0;
        enteredFortressCount = 0;
        enteredFortressTime = 0;
        firstPortalCount = 0;
        firstPortalTime = 0;
        enteredStrongholdCount = 0;
        enteredStrongholdTime = 0;
        enteredEndCount = 0;
        enteredEndTime = 0;
        finishedCount = 0;
        finishedTime = 0;
    }
}
