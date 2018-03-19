public class Message {
    private int ID;
    private String direction;
    private int hopCount;
    private String leaderInformation = "";

    public Message(int ID) {
        this.ID = ID;
    }

    public Message(String leaderInformation) {
        this.leaderInformation = leaderInformation;
    }

    public Message(int ID, String direction, int hopCount) {
        this.ID = ID;
        this.direction = direction;
        this.hopCount = hopCount;
    }

    public int getID() {
        return ID;
    }

    public String getDirection() {
        return direction;
    }

    public int getHopCount() {
        return hopCount;
    }

    public String getLeaderInformation() {
        return leaderInformation;
    }

}
