package apps.betan9ne.flagnotflag.helper;

public class leader_item {
    String name, comment;
    String id;
    public leader_item(){}

    public leader_item(String comment, String name, String id)
    {
        this.comment = comment;
        this.name = name;
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        this.name = name;
    }
}
