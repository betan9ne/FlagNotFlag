package apps.betan9ne.flagnotflag.helper;

public class leader_item {
    String name;
    int score, time;
    public leader_item(){}

    public leader_item(String name, int score, int time)
    {
        this.score = score;
        this.name = name;
        this.time = time;

    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
