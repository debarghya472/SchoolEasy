package android.example.schooleasy.dataclass;

public class DisQuestionReply {
    private String name;
    private String qs;

    public String getName() {
        return name;
    }

    public String getQs() {
        return qs;
    }

    public DisQuestionReply(String name, String qs) {
        this.name = name;
        this.qs = qs;
    }

}
