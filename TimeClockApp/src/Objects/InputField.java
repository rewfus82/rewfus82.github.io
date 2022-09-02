package Objects;

import java.awt.Dimension;

public class InputField extends TextField {

    public InputField(String str) {
        super(str);
        this.setPreferredSize(new Dimension(200,50));
    }


}
