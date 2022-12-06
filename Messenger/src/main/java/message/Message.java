package message;

import java.io.Serializable;

public class Message implements Serializable {
    private String text;
    private String name;

    public Message(String text, String name)
    {
        this.text = text;
        this.name = name;
    }
    public Message(String text)
    {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
